import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score
from sklearn.metrics.pairwise import cosine_similarity
import MeCab

#乱数の固定
SEED = 42

#データ読み取り
data = pd.read_csv('wrime-ver1.tsv', sep='\t')
f = open('getText.txt', encoding='UTF-8')
save_file = open('result.txt', encoding='UTF-8', mode='w')


#各値の取得
target1 = (data['Avg. Readers_Joy'].values > 0).astype(int)
target2 = (data['Avg. Readers_Sadness'].values > 0).astype(int)
target3 = (data['Avg. Readers_Anticipation'].values > 0).astype(int)
target4 = (data['Avg. Readers_Surprise'].values > 0).astype(int)
target5 = (data['Avg. Readers_Anger'].values > 0).astype(int)
target6 = (data['Avg. Readers_Fear'].values > 0).astype(int)
target7 = (data['Avg. Readers_Disgust'].values > 0).astype(int)
target8 = (data['Avg. Readers_Trust'].values > 0).astype(int)
user_ids = data["UserID"].values
sentences = data["Sentence"].values

#listの作成
idx_lst = list(range(len(target1)))

#データの分割(train=64%,test=20%,valid=16%)
_train_idx, test_idx, _, _ = train_test_split(idx_lst, idx_lst, test_size=0.2, random_state=SEED, stratify=user_ids)
train_idx, valid_idx, _, _ = train_test_split(_train_idx, _train_idx, test_size=0.2, random_state=SEED, stratify=user_ids[_train_idx])

#使うデータの抜粋
train_texts = sentences[train_idx]
valid_texts = sentences[valid_idx]
test_texts = sentences[test_idx]

# for i in range(1,9):
#   exec_command = 'y'

y1_train = target1[train_idx]
y1_valid = target1[valid_idx]
y1_test = target1[test_idx]

y2_train = target2[train_idx]
y2_valid = target2[valid_idx]
y2_test = target2[test_idx]

y3_train = target3[train_idx]
y3_valid = target3[valid_idx]
y3_test = target3[test_idx]

y4_train = target4[train_idx]
y4_valid = target4[valid_idx]
y4_test = target4[test_idx]

y5_train = target5[train_idx]
y5_valid = target5[valid_idx]
y5_test = target5[test_idx]

y6_train = target6[train_idx]
y6_valid = target6[valid_idx]
y6_test = target6[test_idx]

y7_train = target7[train_idx]
y7_valid = target7[valid_idx]
y7_test = target7[test_idx]

y8_train = target8[train_idx]
y8_valid = target8[valid_idx]
y8_test = target8[test_idx]


#例文の追加
test_sentences = []
text = f.read()
test_sentences.append(text)

#分かち書き
tagger = MeCab.Tagger ("-Owakati")
train_wakati = []
valid_wakati =[]
test_wakati = []
text_wakati = []

for sentence in train_texts:
    w = tagger.parse(sentence)
    train_wakati.append(w)

for s in valid_texts:
    l = tagger.parse(s)
    valid_wakati.append(l)

for sen in test_texts:
    y = tagger.parse(sen)
    test_wakati.append(y)

for t in test_sentences:
    p = tagger.parse(t)
    text_wakati.append(p)

#TfidVectorizer
vectorizer = TfidfVectorizer()
vectorizer = TfidfVectorizer(token_pattern=u'(?u)\\b\\w+\\b')

#TF-IDFの処理を実行
# X_all = vectorizer.fit_transform(all_wakati)
X_train = vectorizer.fit_transform(train_wakati)
X_valid = vectorizer.transform(valid_wakati)
X_test = vectorizer.transform(test_wakati)
X_text = vectorizer.transform(text_wakati)

#ロジスティック回帰の定数決め
#喜び
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y1_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y1_valid, y_pred_valid)
#   print(f"C={c} : Joy_Validation Accuracy = {valid_acc:.4}")

# #悲しみ
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y2_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y2_valid, y_pred_valid)
#   print(f"C={c} : Sadness_Validation Accuracy = {valid_acc:.4}")

# #期待
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y3_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y3_valid, y_pred_valid)
#   print(f"C={c} : Anticipation_Validation Accuracy = {valid_acc:.4}")

# #驚き
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y4_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y4_valid, y_pred_valid)
#   print(f"C={c} : Surprise_Validation Accuracy = {valid_acc:.4}")

# #怒り
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y5_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y5_valid, y_pred_valid)
#   print(f"C={c} : Anger_Validation Accuracy = {valid_acc:.4}")

# #恐れ
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y6_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y6_valid, y_pred_valid)
#   print(f"C={c} : Fear_Validation Accuracy = {valid_acc:.4}")

# #嫌悪
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y7_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y7_valid, y_pred_valid)
#   print(f"C={c} : Disgust_Validation Accuracy = {valid_acc:.4}")

# #信頼
# for c in [0.1, 0.3, 1.0, 3.0, 10.0]:
#   lr = LogisticRegression(C=c, random_state=SEED, n_jobs=-1)
#   lr.fit(X_train, y8_train)
#   y_pred_valid = lr.predict(X_valid)
#   valid_acc = accuracy_score(y8_valid, y_pred_valid)
#   print(f"C={c} : Validation Accuracy = {valid_acc:.4}")

#喜び
lr = LogisticRegression(C=3, random_state=SEED, n_jobs=-1, max_iter=40000)
lr.fit(X_train, y1_train)

y_pred_test = lr.predict(X_test)
test_acc = accuracy_score(y1_test, y_pred_test)

print(f"Joy_Test Accuracy = {test_acc}")

y_pred_text1 = lr.predict(X_text)
print("Joy:",y_pred_text1)

#悲しみ
lr.fit(X_train, y2_train)

test_acc = accuracy_score(y2_test, y_pred_test)

print(f"Sadness_Test Accuracy = {test_acc}")

y_pred_text2 =lr.predict(X_text)
print("Sadness:",y_pred_text2)

#期待
lr.fit(X_train, y3_train)

test_acc = accuracy_score(y3_test, y_pred_test)

print(f"Anticipation_Test Accuracy = {test_acc}")

y_pred_text3 =lr.predict(X_text)
print("Anticipation:",y_pred_text3)

#驚き
lr.fit(X_train, y4_train)

test_acc = accuracy_score(y4_test, y_pred_test)

print(f"Surprise_Test Accuracy = {test_acc}")

y_pred_text4 =lr.predict(X_text)
print("Surprise:",y_pred_text4)

#怒り
lr.fit(X_train, y5_train)

test_acc = accuracy_score(y5_test, y_pred_test)

print(f"Anger_Test Accuracy = {test_acc}")

y_pred_text5 =lr.predict(X_text)
print("Anger:",y_pred_text5)

#恐れ
lr.fit(X_train, y6_train)

test_acc = accuracy_score(y6_test, y_pred_test)

print(f"Fear_Test Accuracy = {test_acc}")

y_pred_text6 =lr.predict(X_text)
print("Fear:",y_pred_text6)

#嫌悪
lr.fit(X_train, y7_train)

test_acc = accuracy_score(y7_test, y_pred_test)

print(f"Disgust_Test Accuracy = {test_acc}")

y_pred_text7 =lr.predict(X_text)
print("Disgust:",y_pred_text7)

#信頼
lr.fit(X_train, y8_train)

test_acc = accuracy_score(y8_test, y_pred_test)

print(f"Trust_Test Accuracy = {test_acc}")

y_pred_text8 =lr.predict(X_text)
print("Trust:",y_pred_text8)


#ファイルへの書き込み
list1 = np.concatenate([y_pred_text1, y_pred_text2, y_pred_text3, y_pred_text4, y_pred_text5, y_pred_text6, y_pred_text7, y_pred_text8])
result = ['Joy/Sadness/Anticipation/Surprise/Anger/Fear/Disgust/Trust']
print(result, file=save_file)
print(list1, file=save_file)

print(list1)

with open('result.txt') as f:
    print(f.read())

f.close()
save_file.close()