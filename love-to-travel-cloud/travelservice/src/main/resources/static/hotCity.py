from pymongo import MongoClient
import pandas as pd
import pymysql
#连接MongodB
def connectionDB():
    client = MongoClient('mongodb://admin:12345678@121.41.107.144:27017/travelservice?authMechanism=DEFAULT&authSource=admin&authMechanism=SCRAM-SHA-1')
    ##指定要操作的数据库，test
    db = client.travelservice
    ##限定数据库表，plan
    mycol = db["note"]

    #获取SourceCode，直接从MongoDB查询获得
    City = mycol.distinct( "city")
    Date = mycol.distinct("createTime")
    print(City)
    print(Date)
    return City,Date

def GetHotCity ():
    list_city,list_date = connectionDB()
    list_city_new =[]
    for i in range(len(list_city)):
        if int(list_date[i][:4]) > 2016:
            #         print(int(data['date'][i][:4]))
            list_city_new.append(list_city[i])
    list_city_new
    counts = {}         #计数器
    for word in list_city_new:
        counts[word] = counts.get(word, 0) + 1

    counts = sorted(counts.items(), key = lambda x: x[1], reverse = True)

    list_res=[]
    for i in range(10):
        word, count = counts[i]
        list_res.append(word)
    return list_res
#     print(list_res)


def get_conn():
    return pymysql.connect(
        host='47.98.138.0',
        port=3306,
        user='travelservice',
        password='2x8R3WmbCzt6tcEd',
        database='travelservice',
        charset='utf8'
    )

def HotCityData():
    conn = get_conn()
    sql = "select * from city"
    try:
        cursor = conn.cursor()
        cursor.execute(sql)
        list1=list(cursor.fetchall())
    except pymysql.Error as e:
        print('连接失败')

    list_res=GetHotCity ()
    #     list_resf=[]
    #     print(list1)
    #     list1=list(query_data(sql))
    for i in list_res:
        i=i+'市'
        for k in list1:
            if i==k[1]:
                #                 list_resf.append(k)
                sqlInsert="insert hot_city value(%s,%s,%s,%s,%s,%s)"
                value = k
                try:
                    cursor.execute(sqlInsert,value)
                    conn.commit()
                finally:
                    conn.close

#     return list_resf

if __name__ == "__main__":
    HotCityData()
