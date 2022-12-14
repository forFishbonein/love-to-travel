import pandas as pd

def get_conn():
    return pymysql.connect(
        host='47.98.138.0',
        port=3306,
        user='travelservice',
        password='2x8R3WmbCzt6tcEd',
        database='travelservice',
        charset='utf8'
    )

def GetHotSecnery():
    conn = get_conn()
    sql = "select * from scenery"
    list1 = []
    cursor = conn.cursor()
    try:
        cursor.execute(sql)
        list1 = list(cursor.fetchall())
    except pymysql.Error as e:
        print('连接失败')

    list_score = []
    for i in range(0,len(list1)):
        list_score.append(list1[i][3])
    list_id = []
    for i in range(0,len(list1)):
        list_id.append(list1[i][0])
    df_sce = pd.DataFrame(columns=['id', 'score'], index=range(0, len(list_id)))
    for i in range(0, len(list_id)):
        df_sce.iloc[i, 0] = list_id[i]
        df_sce.iloc[i, 1] = list_score[i]
    sorted_sce_df = df_sce.sort_values(by="score", ascending=False)
    sorted_sce_df = sorted_sce_df[:10]

    return sorted_sce_df


GetHotSecnery()

