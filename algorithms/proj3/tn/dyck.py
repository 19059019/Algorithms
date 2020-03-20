from itertools import product
import sys


def solve(num, cache={0: [''], 1: ['X'], 2: ['X(X)', '(X)X']}):
    if num not in cache:
        cache[num] = ['(%s)X(%s)' % t for i in range(1, num + 1)
                      for t in product(solve(i - 1), solve(num - i))]
    return cache[num]


def get_tree(n):
    i = 0
    stop = False
    for num in range(0, 100):
        for s in solve(num):
            if i == n:
                print("{}, {},".format(i,s.replace("()", "")))
                stop = True
                break
            i = i+1
        if stop == True:
            break


# i = 1
# for s in solve(16):
# 	i+=1
# 	print(s.replace("()",""))


if __name__ == "__main__":
    num = sys.argv[1]
    get_tree(int(num))


# Links
# https://stackoverflow.com/questions/4313921/recurrence-approach-h)ow-can-we-generate-all-possibilities-on-braces
