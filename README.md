# Web Data Integration Project 23 (Group 9)

## Introduction
This project focuses on integrating web data, utilizing datasets from various sources and employing advanced data integration techniques. Our goal is to create a unified, accurate, and comprehensive dataset from disparate sources.

## Datasets
We have obtained three datasets for this project:
- Two datasets from Kaggle.
- One dataset extracted from DBpedia using SPARQL queries.

## Process Overview
- **Data Mapping and Translation:** Utilizing Altova MapForce tools, we have mapped and translated the collected data into a designed integrated schema. This schema is optimized to fully cover all records while minimizing the number of elements.
- **Identity Resolution:** We implemented identity resolution using multiple comparator strategies. This process identifies matching entities that refer to the same real-world entities.
- **Gold Standard Definition:** A gold standard has been manually defined, covering a wide range of corner cases.

## Data Fusion
- **Fusion of Best Results:** The best two results from the identity resolution process's correspondences.csv were chosen for data fusion.
- **Fusion Strategies:** Multiple fusion strategies were implemented to integrate these datasets.
- **Evaluation:** We evaluated the fused data against the gold standard, which was enhanced with additional data from Google and DBpedia to ensure accuracy.

## Extra Notes
- **Embedded Similarity Measurement:** This feature was implemented but found unsuitable for our case study and thus not used in the identity resolution process.
- **Optional Use:** If interested, you can download the pre-trained embedded model from fastText (with this following link as https://fasttext.cc/docs/en/crawl-vectors.html the English version bin file type). Place it in the specified location and uncomment the relevant sections in the code for it to function.

## How to Use
1. Clone the repository.

## Contributed 

This project was contributed by few members can be found in the contributor list.

---
