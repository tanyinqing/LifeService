package com.linyou.lifeservice.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.customdialog.PopSexDialog;
import com.linyou.lifeservice.customdialog.PopWinDialog;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.model.UserInfoModel;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.wheel.WheelSex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ContentView(R.layout.activity_userinfo)
public class UserInfoActivity extends TitleBarActivity {

	private UserInfoModel mUserInfoModel;

	@ViewInject(R.id.linearHead)
	private LinearLayout linearHead;

	@ViewInject(R.id.linearNick)
	private LinearLayout linearNick;

	@ViewInject(R.id.linearSex)
	private LinearLayout linearSex;

	@ViewInject(R.id.linearModifyPwd)
	private LinearLayout linearModifyPwd;
	
	@ViewInject(R.id.imageHead)
	private ImageView imageHead;

	@ViewInject(R.id.textSex)
	private TextView textSex;
	@ViewInject(R.id.editNick)
	private EditText editNick;
	
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    // 创建一个以当前时间为名称的文件
    File tempFile = new File(Environment.getExternalStorageDirectory(),getPhotoFileName());

	private PopWinDialog popWinDialog;

	@OnClick(R.id.linearHead)
	void head(View v) {
		popWinDialog = new PopWinDialog(this, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i;
				switch (v.getId()) {
				case R.id.btn_pick_photo:
					Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                    startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
					break;
				case R.id.btn_take_photo:
                    // 调用系统的拍照功能
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 指定调用相机拍照后照片的储存路径
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFile));
                    startActivityForResult(intent2, PHOTO_REQUEST_TAKEPHOTO);
					break;
				default:
					break;
				}

				popWinDialog.dismiss();
			}

		});
		popWinDialog.showAtLocation(v, Gravity.BOTTOM, 0, 0);
	}

	@OnClick(R.id.linearModifyPwd)
	void modifyPwd(View v) {
		startActivityByClass(ModifyPassWordActivity.class);
	}

	private PopSexDialog dialog;

	@OnClick(R.id.linearSex)
	void sex(View v) {

		PublicUtil.closeKeyBoard(this);
		final WheelSex wheelData = new WheelSex(this);
		dialog = new PopSexDialog(this, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				textSex.setText("" + wheelData.getSex());
			}
		});
		dialog.setTitle("选择性别");
		dialog.setView(wheelData.getView());
		dialog.showAtLocation(v, Gravity.BOTTOM, 0, 0);
	}

	@OnClick(R.id.buttonSave)
	void save(View v) {

		mUserInfoModel.modifyUserInfo(editNick.getText().toString(), textSex
				.getText().toString());
	}

	@Override
	void setListeners() {
	}
	
	

	private void updateUI() {
		User u = mUserInfoModel.getUser();
		if (u == null) {
			editNick.setText("");
			editNick.setHint("请输入昵称");
			textSex.setText("请选择性别");
		} else {
			//if (null == u.getNickName()) {  这是原来的昵称名称
			if (null == u.getUserName()) {
				editNick.setText("");
				editNick.setHint("请输入昵称");
			} else {

				editNick.setText("" + u.getUserName());
			}
			PublicUtil.getImageLoader().displayImage(Constant.URLBASE+u.getHeadportrait(), imageHead,PublicUtil.getHeadOption());
			if (null == u.getSex()) {
				textSex.setText("请选择性别");
			} else {
				if (u.getSex().equals("1")){
					textSex.setText("男");
				}else if (u.getSex().equals("2")){
					textSex.setText("女");
				}

			}
		}
	}

	
	
	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_info);
		mUserInfoModel = new UserInfoModel(this);
		updateUI();
	}

	@Override
	void RightButtonClicked() {

	}

	@Override
	void LeftButtonClicked() {
		finish();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
        case PHOTO_REQUEST_TAKEPHOTO:
            startPhotoZoom(Uri.fromFile(tempFile), 300);
            break;

        case PHOTO_REQUEST_GALLERY:
            if (data != null)
                startPhotoZoom(data.getData(), 300);
            break;

        case PHOTO_REQUEST_CUT:
        	System.out.println("PHOTO_REQUEST_CUT -----------> " +PHOTO_REQUEST_CUT);
            if (data != null)
                setPicToView(data);
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


	
	private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    //剪裁后的图片显示到UI界面上并上传
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            final Bitmap photo = bundle.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
//            imageHead.setBackgroundDrawable(drawable);
            imageHead.setImageDrawable(drawable);
            
            new Handler().postAtFrontOfQueue(new Runnable()
            {
                public void run()
                {
                	tempFile = getFile(photo);
                }
            });
            mUserInfoModel.uploadHead(getFile1(photo));
        }
    }




	//把图片文件转化为文件输出流,
    private File getFile(Bitmap bitmap)
    {
    	InputStream in = Bitmap2InputStream(bitmap,100);
    	File file = new File(tempFile.getPath());//创建文件 
    	if (!file.exists()) { 
    	    try {//如果文件不存在就创建文件，写入图片 
    	        file.createNewFile(); 
    	        FileOutputStream fo = new FileOutputStream(file); 
    	        int read = in.read(); 
    	        while (read != -1) { 
    	                fo.write(read); 
    	                read = in.read(); 
    	        } 
    	        //关闭流
    	        fo.flush();
    	        fo.close(); 
    	        in.close(); 
    	    } catch (IOException e) { 
    	        e.printStackTrace(); 

    	    } 
    	}
    	return file;
    }

	//把图片文件转化为Base64编码的字符串
	private String getFile1(Bitmap bm)
	{
		ByteArrayOutputStream stream=new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG,100,stream);
		byte[]bytes=stream.toByteArray();
		String img=new String(Base64.encodeToString(bytes,Base64.DEFAULT));
		return img;
	}

	//将图片转化成byte数组  再转化成Base64编码的字符串数据此步没做
	//将bitmap的数据放送到指定服务器
    private InputStream Bitmap2InputStream(Bitmap bm, int quality) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  

        return is;  

    }  

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
//        return dateFormat.format(date) + ".jpg";
        return "tmp.jpg";
    }

}
