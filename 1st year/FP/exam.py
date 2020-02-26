def swap_chr(text, i, j):
    text = list(text)
    text[i], text[j] = text[j], text[i]
    return "".join(text)

text = "abcdefgh"
print(text)
print(swap_chr(text, 0, -1))