package com.example.administrator.intentstudy;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * intent-filter的匹配规则：（action、category和data必须同时具备，category默认为category.DEFAULT，data的Uri中的scheme默认值为file和content）
 * action可以单独出现（因为在这种情况下android的category默认为android.intent.category.DEFAULT;data也为默认值）；匹配其中的一个action成功即可启动activity；
 * category 每一个category必须都匹配成功才可以启动actvity；
 * data不可以单独出现；
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button byAction = (Button) findViewById(R.id.ByAction);
        Button byActionAndCategory = (Button) findViewById(R.id.ByActionAndCategory);
        Button byActionAndData = (Button) findViewById(R.id.ByActionAndData);
        Button byCategory = (Button) findViewById(R.id.ByCategory);
        Button byData = (Button) findViewById(R.id.ByData);
        Button byImage = (Button) findViewById(R.id.ByImage);
        Button byAudio = (Button) findViewById(R.id.ByAudio);
        byAction.setOnClickListener(this);
        byActionAndCategory.setOnClickListener(this);
        byActionAndData.setOnClickListener(this);
        byCategory.setOnClickListener(this);
        byData.setOnClickListener(this);
        byImage.setOnClickListener(this);
        byAudio.setOnClickListener(this);
    }

    private void intentByAction(){
        //在清单文件中必须设置<category android:name="android.intent.category.DEFAULT"/>
        //android 在代码中categroy默认为DEFAULT
        //如果自己定义的某个Activity要通过隐式启动，在AndroidManifast.xm那么必须加上android.intent.category.DEFAULT，否则不起作用
        //清单文件中action只要有一个配上，即可启动activity
        Intent intent=new Intent();
        intent.setAction("ssss");
        startActivity(intent);
    }

    private void intentByCategroy(){
        //每个intent中必须有一个action
        //清单文件中的每一个category，必须和代码中的intent的categroy一一对应，必不可少。
        Intent intent=new Intent();
        intent.setAction("abc");
        intent.addCategory("abc");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }
    private void intentByData(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri=Uri.parse("http://blog.csdn.net/wangliang198901/article/details/12320221");
        intent.setData(uri);
        startActivity(intent);
    }
    private void intentByActionAndCategory() {
        /*每一个通过 startActivity() 方法发出的隐式 Intent 都至少有一个 category，
        就是 "android.intent.category.DEFAULT"，所以只要是想接收一个隐式 Intent 的 Activity 都应该包括 "android.intent.category.DEFAULT" category，
        不然将导致 Intent 匹配失败。
        //TODO 从上面的论述还可以获得以下信息：
        1、一个 Intent 可以有多个 category，但至少会有一个，也是默认的一个 category。
        2、只有 Intent 的所有 category 都匹配上，Activity 才会接收这个 Intent。*/
        Intent intent = new Intent();
        intent.setAction("sss");
        intent.addCategory("categroy");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }
    private void intentByActionAndData(){
        //data 由uri和mimeType两部分组成；uri中的scheme默认值为file和content；mimetype为文件类型
        /**
         * scheme的用法：
         *【tel://】：号码数据格式，后跟电话号码
         *【mailto://】：邮件数据格式，后跟邮件收件人地址
         *【smsto://】：短息数据格式，后跟短信接收号码
         *【content://】：内容数据格式，后跟需要读取的内容
         *【file://】：文件数据格式，后跟文件路径
         *【market://search?q=pname:包名】：市场数据格式，在Google Market里搜索指定包名的应用
         *【market://details?id=包名】：市场数据格式，在Google Market里跳转到指定包名应用的详情页
         *【geo://latitude,longitude】:经纬数据格式，在地图上显示经纬度指定的位置
         *【mqqwpa://im/chat?chat_type=wpa&uin=909120849&version=1"】：和指定QQ聊天
         *【http://】：HTML链接
         *
         * mimetype的用法：
         * 一个MIME Type由媒体类型(type)与子类型(subtype)组成，它们之间使用反斜杠/分割，形式例如：
         * audio/mp3 音频文件，后缀为mp3的文件
         * audio/* 音频文件，*为通配符
         *
         * ①type有下面的形式。
         *Text：用于标准化地表示的文本信息，文本消息可以是多种字符集和或者多种格式的；
         *Multipart：用于连接消息体的多个部分构成一个消息，这些部分可以是不同类型的数据；
         *Application：用于传输应用程序数据或者二进制数据；
         *Message：用于包装一个E-mail消息；
         *Image：用于传输静态图片数据；
         *Audio：用于传输音频或者音声数据；
         *Video：用于传输动态影像数据，可以是与音频编辑在一起的视频数据格式。
         *
         *②subtype的形式：
         * text/plain（纯文本）
         *text/html（HTML文档）
         *application/xhtml+xml（XHTML文档）
         *image/gif（GIF图像）
         *image/jpeg（JPEG图像）【PHP中为：image/pjpeg】
         *image/png（PNG图像）【PHP中为：image/x-png】
         *video/mpeg（MPEG动画）
         *application/octet-stream（任意的二进制数据）
         *application/pdf（PDF文档）
         *application/msword（Microsoft Word文件）
         *message/rfc822（RFC 822形式）
         *multipart/alternative（HTML邮件的HTML形式和纯文本形式，相同内容使用不同形式表示）
         *application/x-www-form-urlencoded（使用HTTP的POST方法提交的表单）
         *multipart/form-data（同上，但主要用于表单提交时伴随文件上传的场合）
         *
         */
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri=Uri.parse("http://blog.csdn.net/wangliang198901/article/details/12320221");
        intent.setData(uri);
        startActivity(intent);
    }
    private void intentByImage(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivity(intent);
    }
    private void intentByAudio(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("audio/*");
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ByAction:
                intentByAction();
                break;
            case R.id.ByActionAndCategory:
                intentByActionAndCategory();
                break;
            case R.id.ByActionAndData:
                intentByActionAndData();
                break;
            case R.id.ByCategory:
                intentByCategroy();
                break;
            case R.id.ByData:
                intentByData();
                break;
            case R.id.ByImage:
                intentByImage();
                break;
            case R.id.ByAudio:
                intentByAudio();
                break;
        }
    }
}
