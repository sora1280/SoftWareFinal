import pandas as pd
from sklearn.model_selection import train_test_split
import tqdm

data = pd.read_csv('wrime-ver2.tsv', sep="\t")
print(data)

targets = (wrime_df['Avg. Readers_Joy'].values > 0).astype(int)
user_ids = wrime_df["UserID"].values
sentences = wrime_df["Sentence"].values

idx_lst = list(range(len(targets)))

_train_idx, test_idx, _, _ = train_test_split(idx_lst, idx_lst, test_size=0.2, random_state=SEED, stratify=user_ids)
train_idx, valid_idx, _, _ = train_test_split(_train_idx, _train_idx, test_size=0.2, random_state=SEED, stratify=user_ids[_train_idx])

train_texts = sentences[train_idx]
valid_texts = sentences[valid_idx]
test_texts = sentences[test_idx]

y_train = targets[train_idx]
y_valid = targets[valid_idx]
y_test = targets[test_idx]

wakati_mecab = WakatiMecab()

train_corpus = [wakati_mecab.wakati(s) for s in tqdm(train_texts)]
valid_corpus = [wakati_mecab.wakati(s) for s in tqdm(valid_texts)]
test_corpus = [wakati_mecab.wakati(s) for s in tqdm(test_texts)]

vectorizer = TfidfVectorizer()
X_train = vectorizer.fit_transform(train_corpus)
X_valid = vectorizer.transform(valid_corpus)
X_test = vectorizer.transform(test_corpus)

for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
    lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
    lr.fit(X_train, y_train)
    y_pred_valid = lr.predict(X_valid)
    valid_acc = accuracy_score(y_valid, y_pred_valid)
    print(f"C={c} : Validation Accuracy = {valid_acc:.4}")