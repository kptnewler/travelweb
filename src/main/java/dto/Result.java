package dto;

/**
 * 封装json对象，所有返回结果都使用它
 */
public class Result<T> {

    private boolean success;// 是否成功标志

    private T data;// 成功时返回的数据

    private String msg;// 错误信息

    public Result() {
    }

    // 成功时的构造器
    public Result(T data) {
        this.success = true;
        this.data = data;
    }

    // 错误时的构造器
    public Result(String msg) {
        this.success = false;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JsonResult [success=" + success + ", data=" + data + ", error=" + msg + "]";
    }

}