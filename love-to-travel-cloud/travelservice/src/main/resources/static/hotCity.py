import numpy as np
a = np.arange(12).reshape(3,4)

from pymongo import MongoClient
import pymysql
def connectionDB():
    client = MongoClient('mongodb://admin:12345678@121.41.107.144:27017/travelservice?authMechanism=DEFAULT&authSource=admin&authMechanism=SCRAM-SHA-1')
    db = client.travelservice
    mycol = db["note"]
    City = mycol.distinct( "city")
    Date = mycol.distinct("createTime")
    return City,Date

def GetHotCity ():
    list_city,list_date = connectionDB()
    list_city_new =[]
    for i in range(len(list_city)):
        list_city_new.append(list_city[i])
    counts ={}
    for word in list_city_new:
        counts[word] = counts.get(word, 0) + 1
    counts = sorted(counts.items(), key = lambda x: x[1], reverse = True)[:10]
    list_res=[]

    for i in range(len(counts)):
        word,count = counts[i]
        list_res.append(word)
    return list_res

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
    list1=[]
    cursor = conn.cursor()
    try:
        cursor.execute(sql)
        list1=list(cursor.fetchall())
    except pymysql.Error as e:
        print('连接失败')

    list_res=GetHotCity ()

    for i in list_res:
        i=i+'市'
        for k in list1:
            if i==k[1]:
                sqlInsert="insert hot_city value(%s,%s,%s,%s)"
                value = [k[0],k[1],k[3],k[4]]
                try:
                    cursor.execute(sqlInsert,value)
                    conn.commit()
                finally:
                    conn.close


HotCityData()
