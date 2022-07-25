import pandas as pd
from sklearn.model_selection import train_test_split

data = pd.read_csv('wrime-ver2.tsv', sep="\t")
print(data)

targets = (wrime_df['Avg. Readers_Joy'].values > 0).astype(int)
user_ids = wrime_df["UserID"].values
sentences = wrime_df["Sentence"].values