import pandas as pd
import numpy as np
from pymongo import MongoClient
import pymysql

def connectionDB():
    client = MongoClient(
        'mongodb://admin:12345678@121.41.107.144:27017/travelservice?authMechanism=DEFAULT&authSource=admin&authMechanism=SCRAM-SHA-1')
    db = client.travelservice
    mycol = db["note"]
    view = mycol.distinct( "view")
    like = mycol.distinct( "like")
    comment = mycol.distinct( "comment")
    noteid = mycol.distinct( "_id")
    return view, like,comment,noteid


def GetHotNote():
    list_view,list_like,list_comment,list_id = connectionDB()
    df2 = pd.DataFrame(columns=['id', 'score'], index=range(0, len(list_id)))
    for i in range(0, len(list_id)):
        df2.iloc[i, 0] = list_id[i]
        df2.iloc[i, 1] = (list_view[i] / 50 + list_like[i] + list_comment[i] * 5 / 2)
    sorted_note_df = df2.sort_values(by="score", ascending=False)
    sorted_note_df = sorted_note_df[:10]
    # print(sorted_note_df)
    return sorted_note_df


GetHotNote()

