import pandas as pd
import matplotlib.pyplot as plt
import sys
sys.path.append("../")
from model import Kronos, KronosTokenizer, KronosPredictor


def plot_prediction(kline_df, pred_df):
    """
    可视化真实数据与预测数据的对比图，包括收盘价和成交量两个子图。

    参数:
        kline_df (pd.DataFrame): 包含历史K线数据的DataFrame，需包含'close'和'volume'列。
        pred_df (pd.DataFrame): 包含模型预测结果的DataFrame，需包含'close'和'volume'列。

    返回值:
        无返回值。该函数直接绘制图形并显示。
    """
    # 对齐预测数据索引与历史数据的最后部分
    pred_df.index = kline_df.index[-pred_df.shape[0]:]

    # 提取收盘价并设置名称
    sr_close = kline_df['close']
    sr_pred_close = pred_df['close']
    sr_close.name = 'Ground Truth'
    sr_pred_close.name = "Prediction"

    # 提取成交量并设置名称
    sr_volume = kline_df['volume']
    sr_pred_volume = pred_df['volume']
    sr_volume.name = 'Ground Truth'
    sr_pred_volume.name = "Prediction"

    # 合并真实值与预测值用于绘图
    close_df = pd.concat([sr_close, sr_pred_close], axis=1)
    volume_df = pd.concat([sr_volume, sr_pred_volume], axis=1)

    # 创建包含两个子图的图表
    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 6), sharex=True)

    # 绘制收盘价对比图
    ax1.plot(close_df['Ground Truth'], label='Ground Truth', color='blue', linewidth=1.5)
    ax1.plot(close_df['Prediction'], label='Prediction', color='red', linewidth=1.5)
    ax1.set_ylabel('Close Price', fontsize=14)
    ax1.legend(loc='lower left', fontsize=12)
    ax1.grid(True)

    # 绘制成交量对比图
    ax2.plot(volume_df['Ground Truth'], label='Ground Truth', color='blue', linewidth=1.5)
    ax2.plot(volume_df['Prediction'], label='Prediction', color='red', linewidth=1.5)
    ax2.set_ylabel('Volume', fontsize=14)
    ax2.legend(loc='upper left', fontsize=12)
    ax2.grid(True)

    # 调整布局并显示图表
    plt.tight_layout()
    plt.show()


# 1. 加载预训练的模型和分词器
tokenizer = KronosTokenizer.from_pretrained("NeoQuasar/Kronos-Tokenizer-base")
model = Kronos.from_pretrained("NeoQuasar/Kronos-small")

# 2. 初始化预测器对象
predictor = KronosPredictor(model, tokenizer, device="cpu", max_context=512)

# 3. 准备输入数据
filename = "DATA_FILE_NAME"
filepath = f"./data/{filename}"
df = pd.read_csv(filepath)
df['timestamps'] = pd.to_datetime(df['timestamps'])

lookback = LOOK_BACK_NUMBER       # 历史数据长度
pred_len = 120       # 预测长度

# 截取历史数据作为输入特征
x_df = df.loc[:lookback-1, ['open', 'high', 'low', 'close', 'volume', 'amount']]
x_timestamp = df.loc[:lookback-1, 'timestamps']
# 获取预测时间戳
y_timestamp = df.loc[lookback:lookback+pred_len-1, 'timestamps']

# 4. 执行预测操作
pred_df = predictor.predict(
    df=x_df,
    x_timestamp=x_timestamp,
    y_timestamp=y_timestamp,
    pred_len=pred_len,
    T=1.0,
    top_p=0.9,
    sample_count=1,
    verbose=True
)

# 5. 输出预测结果并可视化
print("Forecasted Data Head:")
print(pred_df.head())

# 拼接历史数据和预测数据用于绘图展示
kline_df = df.loc[:lookback+pred_len-1]

# 调用绘图函数进行可视化
plot_prediction(kline_df, pred_df)

