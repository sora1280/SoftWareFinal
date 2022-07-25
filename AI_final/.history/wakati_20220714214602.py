import MeCab

class WakatiMecab():

    def __init__(self):
        self.m = MeCab.Tagger ("-Ochasen")

    def __call__(self, text):
        wakati = [w.split("\t") for w in self.m.parse (text).split("\n")[:-2]]
        return wakati

    def wakati(self, text):
        wakati = self.__call__(text)
        wakati = [w[0] for w in wakati]
        return " ".join(wakati)