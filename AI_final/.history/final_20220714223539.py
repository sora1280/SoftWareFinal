import pandas as pd
from sklearn.model_selection import train_test_split
import MeCab
import tqdm

SEED = 42

data = pd.read_csv('wrime-ver2.tsv', sep="\t")

targets = (data['Avg. Readers_Joy'].values > 0).astype(int)
user_ids = data["UserID"].values
sentences = data["Sentence"].values

idx_lst = list(range(len(targets)))

_train_idx, test_idx, _, _ = train_test_split(idx_lst, idx_lst, test_size=0.2, random_state=SEED, stratify=user_ids)
train_idx, valid_idx, _, _ = train_test_split(_train_idx, _train_idx, test_size=0.2, random_state=SEED, stratify=user_ids[_train_idx])

train_texts = sentences[train_idx]
valid_texts = sentences[valid_idx]
test_texts = sentences[test_idx]

y_train = targets[train_idx]
y_valid = targets[valid_idx]
y_test = targets[test_idx]

tagger = MeCab.Tagger ("-Owakati")
train_wakati = []
train_wakati.append(w.split("\t") for w in tagger.parse (train_texts).split("\n"))
valid_wakati =[]
train_wakati.append(w.split("\t") for w in tagger.parse (valid_texts).split("\n"))
test_wakati = []
test_wakati.append(w.split("\t") for w in tagger.parse (test_texts).split("\n"))

vectorizer = TfidfVectorizer(token_pattern=u'(?u)\\b\\w+\\b')

X_train = vectorizer.fit_transform(train_wakati)
X_valid = vectorizer.transform(valid_wakati)
X_test = vectorizer.transform(test_wakati)

for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
    lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
    lr.fit(X_train, y_train)
    y_pred_valid = lr.predict(X_valid)
    valid_acc = accuracy_score(y_valid, y_pred_valid)
    print(f"C={c} : Validation Accuracy = {valid_acc:.4}")

lr = LogisticRegression(C=3, random_state=SEED, n_jobs=-1)
lr.fit(X_train, y_train)

y_pred_test = lr.predict(X_test)
test_acc = accuracy_score(y_test, y_pred_test)

print(f"Test Accuracy = {test_acc}")