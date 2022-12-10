import pandas as pd
import numpy as np
import csv

def read_data(path):
    df = pd.read_excel(path)
    senery_df0 = df['中文名称']
    senery_df2 = df['评分']
    senery_df = pd.DataFrame(columns=['中文名称', '评分'], index=range(0, len(df)))
    for i in range(0, len(df)):
        senery_df.iloc[i, 0] = senery_df0[i]
        senery_df.iloc[i, 1] = senery_df2[i]
    sorted_senery_df = senery_df.sort_values(by="评分", ascending=False)
    result_senery_df = sorted_senery_df[:10]
    return result_senery_df

def main():
    PATH = 'D:\python_jupyter\推荐算法\datasets\ml-latest-small\SceneryDetail.xlsx'
    result = read_data(PATH)
    pass


if __name__ == '__main__':
    main()
