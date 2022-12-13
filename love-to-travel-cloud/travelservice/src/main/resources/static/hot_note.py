import pandas as pd


def read_data(path):
    df1 = pd.read_excel(path)
    list_view = df1['view']
    list_like = df1['like']
    list_comment = df1['comment']
    df2 = pd.DataFrame(columns=['title', 'score'], index=range(0, len(df1)))
    for i in range(0, len(df1)):
        df2.iloc[i, 0] = df1['title'][i]
        df2.iloc[i, 1] = (list_view[i] / 50 + list_like[i] + list_comment[i] * 5 / 2)
    sorted_note_df = df2.sort_values(by="score", ascending=False)
    sorted_note_df = sorted_note_df[:10]
    # print(sorted_note_df)
    return sorted_note_df


def main():
    PATH = 'D:\python_jupyter\推荐算法\datasets\output\游记最终版.xlsx'
    result = read_data(PATH)
    pass


if __name__ == '__main__':
    main()
