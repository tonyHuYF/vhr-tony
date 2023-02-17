package com.ll.vhr.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误类型
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    public static final Error internal_server_error = new Error(40000, "服务器内部错误");
    public static final Error unauthorized_access = new Error(40001, "当前用户未登录");
    public static final Error field_notnull = new Error(40002, "不能为空");
    public static final Error primary_key_error = new Error(40003, "主键重复错误");
    public static final Error length_error = new Error(40004, "字段超长");
    public static final Error format_error = new Error(40005, "字段格式错误");
    public static final Error not_found_error = new Error(40006, "数据未找到");
    public static final Error mobile_exists = new Error(40007, "手机号码已存在");
    public static final Error verification_code_expire = new Error(40008, "验证码已过期，请重新获取");
    public static final Error verification_code_wrong = new Error(40009, "验证码错误");
    public static final Error method_args_error = new Error(40010, "参数错误");
    public static final Error http_error = new Error(40011, "请求错误");
    public static final Error unauthorized_data_error = new Error(40012, "无权访问该资源");
    public static final Error black_player = new Error(40013, "黑名单用户");
    public static final Error common_error = new Error(40014, "发生错误");
    public static final Error method_not_support = new Error(40015, "访问路径错误，请检查URL和METHOD TYPE是否正确");
    public static final Error cache_setting_error = new Error(40016, "缓存配置错误，请检查");
    public static final Error cache_increment_error = new Error(40017, "缓存increment服务只支持redis，请检查缓存配置");
    public static final Error secret_type_error = new Error(40018, "加密类型配置错误，请修改成MD5或者RSA");
    public static final Error rsa_decrypt_error = new Error(40019, "RSA解密失败，请检查您的加密内容");
    public static final Error read_file_stream_error = new Error(40020, "读取文件流错误");
    public static final Error access_token_error = new Error(40021, "登录凭证失效，请重新登录。");


    public static final Error data_bind_error = new Error(20001, "该数据有关联数据，操作失败");
    public static final Error exists_child_department_error = new Error(20002, "该部门下有子部门，操作失败");
    public static final Error exists_employee_error = new Error(20003, "该部门下有员工，操作失败");


    public static final Error username_not_exist = new Error(30001, "用户名不存在!");

    private int code;
    private String message;


}
