# uidemo
## guide view
效果图：
没显示guide
![image](https://raw.githubusercontent.com/mahongli05/uidemo/master/image/guide_hide.png)
显示guide高亮
![image](https://raw.githubusercontent.com/mahongli05/uidemo/master/image/guide_show.png)

实现透明蒙层的引导GuideTargetView，具体功能如下：
 1. 使目标区域完全透明，其他区域半透明
 2. 前区域穿透点击：
 
使用方式：
 1. 设置目标区域。
    	View view = findViewById(R.id.content_root);
		Rect rect = ViewUtil.getRelativeRect(view, mTargetView);
    	mGuideTargetView.updateTargetViewRelativeRect(view, rect);
    其中content_root是用来计算相对位置的一个参考view，mTargetView是需要高亮（全透明）显示的view。先计算mTargetView相对参考view的位置，在
    在GuideTargetView中调整高亮的显示位置。
 2. 设置目标区域点击事件

   		mGuideTargetView.setOnTargetClickListener(new OnTargetClickListener() {
  
  			@Override
  			public void onTargetClick() {
  				mGuideContainer.setVisibility(View.GONE);
  				mTargetView.setText(getString(R.string.show_guide));
  			}
  		});
  		
注意：
  1. 将guide相关的view放到一个容器中，且初始设置为INVISIBLE。直接设置为GONE，在调用updateTargetViewRelativeRect时无法得到正确位置。也可以
  先设置为GONE，但是在updateTargetViewRelativeRect前需要让guideview先layout
  2. GuideTargetView的高度最好指定，且比目标区域大就行了。
