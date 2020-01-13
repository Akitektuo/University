def compute_sequence_recursive(sequence, n):
    if sequence == []:
        print([])
        return [[]]
    x = compute_sequence_recursive(sequence[1:], n)
    new = [[sequence[0]] + y for y in x]
    for e in new:
        if sum(e) % n == 0:
            print(e)
    return x + new

def isRecorded(sequence, history):
    for h in history:
        if sequence == h:
            return True
    return False
            
def compute_sequence_iterative(sequence, n):
    generated = []
    print(generated)
    for i in range(len(sequence) + 1):
        for j in range(len(sequence)):
            last_used_index = j
            generated.clear()
            generated.append(sequence[j])
            for k in range(last_used_index + 1, len(sequence)):
                if len(generated) == i:
                    break
                num = sequence[k]
                if (sum(generated) + num) % n == 0:
                    last_used_index = k
                    generated.append(num)
            if len(generated) == i and sum(generated) % n == 0:
                print(generated)
    print(sequence)

compute_sequence_recursive([1, 2, 3, 4], 2)