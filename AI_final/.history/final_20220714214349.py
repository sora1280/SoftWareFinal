import pandas as pd

data = pd.read_csv('wrime-ver2.tsv', sep="\t")
print(data)

targets = (wrime_df['Avg. Readers_Joy'].values > 0).astype(int)
user_ids = wrime_df["UserID"].values
sentences = wrime_df["Sentence"].values