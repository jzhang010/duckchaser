#!/usr/bin/env python
# coding: utf-8

# In[ ]:


import tensorflow as tf
from tensorflow import keras

# load images
import tensorflow_datasets as tfds
from tensorflow.keras.utils import to_categorical
import glob

from PIL import Image
import os, os.path

train_imgs = []
path=('/Classes/CS50 Final Project/Images/train/duck')
valid_images = [".jpg",".png"]
for f in os.listdir(path):
    ext = os.path.splitext(f)[1]
    if ext.lower() not in valid_images:
        continue
    train_imgs.append(Image.open(os.path.join(path,f)))

test_imgs = []
path=('/Classes/CS50 Final Project/Images/test/other')
valid_images = [".jpg",".png"]
for f in os.listdir(path):
    ext = os.path.splitext(f)[1]
    if ext.lower() not in valid_images:
        continue
    test_imgs.append(Image.open(os.path.join(path,f)))
    
path=('/Classes/CS50 Final Project/Images/train/duck')
valid_images = [".jpg",".png"]
for f in os.listdir(path):
    ext = os.path.splitext(f)[1]
    if ext.lower() not in valid_images:
        continue
    test_imgs.append(Image.open(os.path.join(path,f)))

train_imgs = tf.image.resize(train_imgs, (150, 150))
test_imgs = tf.image.resize(test_imgs, (150, 150))

# set labels
train_labels = tf.constant([1, 1, 1, 1, 1])
test_labels = tf.constant([1, 1, 1, 0, 0, 0])

# import VGG16
from tensorflow.keras.applications.vgg16 import VGG16
from tensorflow.keras.applications.vgg16 import preprocess_input

# Removes classification layer trained on ImageNet dataset
base_model = VGG16(weights="imagenet", include_top=False, input_shape=train_ds[0].shape)
base_model.trainable = False

train_features = preprocess_input(train_imgs)
test_features = preprocess_input(test_imgs)
                              
# Add last layers
from tensorflow.keras import layers, models
                              
flatten_layer = layers.Flatten()
dense_layer_1 = layers.Dense(10, activation='relu')
dense_layer_2 = layers.Dense(5, activation='relu')
prediction_layer = layers.Dense(2, activation='softmax')
                              
model = models.Sequential([
    base_model,
    flatten_layer,
    dense_layer_1,
    dense_layer_2,
    prediction_layer
])

# Compile and fit model
from tensorflow.keras.callbacks import EarlyStopping

model.compile(
    optimizer='adam',
    loss='categorical_crossentropy',
    metrix=['accuracy'],
)

es = EarlyStopping(monitor='val_accuracy', mode='max', patience=5, restore_best_weights=True)
                              
model.fit(train_features, train_labels, epochs=20, callback=[es])


# In[ ]:




