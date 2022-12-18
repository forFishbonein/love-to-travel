import sys

def fun(a,b):
    return a,b

if __name__ == '__main__':
    a=[]
    for i in range(1,len(sys.argv)):
        a.append((int(sys.argv[i])))

    print(fun(a[0],a[1]))