from calculateSimilar import calculateSimilarIterm4User,calculateSimilarUser4Iterm
import sys
def SceneryRec(usrNo):

    # usrNo = input("请输入用户编号（如'1', '627', '1923'）：")
    res = calculateSimilarIterm4User(usrNo,num=10)
    # print('用户编号：' + usrNo + '推荐的前10个物品为：')
    for item in res:
        if item==None:
            res.remove(item)
    print(res)
    return res

def UserRec(itermNo):
    res = calculateSimilarUser4Iterm(itermNo,num=10)
    for item in res:
        if item==None:
            res.remove(item)
    # itermNo = input("请输入景区编号（如'1', '283', '912', '10237'）：")
    # print('景区编号：' + itermNo + '推荐的前10个用户为：')
    print(res)
    return res

SceneryRec(usrNo='1')
UserRec(itermNo='189')