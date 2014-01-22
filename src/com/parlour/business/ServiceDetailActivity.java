package com.parlour.business;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.parlour.business.model.Service;
import com.parlour.business.model.ServiceImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ServiceDetailActivity extends BaseActivity{
	
	private Service service;
	private TextView serviceTitle;
	private TextView serviceDesc;
	private TextView servicePrice;
	private TextView serviceDuration;
	private TextView serviceNote;
	private ViewFlipper viewFlipper;
//	private static final int SWIPE_MIN_DISTANCE = 120;
//  private static final int SWIPE_THRESHOLD_VELOCITY = 200;  
//  private Context context;
    private float lastX;
    
//	private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		serviceTitle = (TextView)findViewById(R.id.serviceTitle);
		serviceDesc = (TextView)findViewById(R.id.serviceDescription);
		servicePrice = (TextView)findViewById(R.id.servicePrice);
		serviceDuration = (TextView)findViewById(R.id.serviceDuration);
		serviceNote = (TextView)findViewById(R.id.serviceNote);
		viewFlipper = (ViewFlipper)findViewById(R.id.flipper);
//		context = this;
		
		service = (Service)getIntent().getSerializableExtra("service");
		if(service != null) {
			serviceTitle.setText(service.getServiceName());
			serviceDesc.setText("Description: " + service.getServiceDesc());
			servicePrice.setText("Price: " + service.getServicePrice()+ "$");
			serviceDuration.setText("Duration: " + service.getServiceDuration() + "min(s)");
			serviceNote.setText("Note: " + service.getServiceNote());
			
			if(!service.getServiceImages().isEmpty()){
				for(ServiceImage image : service.getServiceImages()) {
					ImageView imageView = new ImageView(getApplicationContext());
					UrlImageViewHelper.setUrlDrawable(imageView,image.getImageUrl(), R.drawable.hair_styling1, new UrlImageViewCallback() {
					    @Override
					    public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
					        viewFlipper.addView(imageView);
					    }
					});					
				}
			}
			
//			viewFlipper.setOnTouchListener(new OnTouchListener() {
//	            @Override
//	            public boolean onTouch(final View view, final MotionEvent event) {
//	                detector.onTouchEvent(event);
//	                return true;
//	            }
//	        });
			
			viewFlipper.setFlipInterval(5000);
			viewFlipper.startFlipping();
		}
		
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.service_detail_layout;
	}
	
//	class SwipeGestureDetector extends SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            try {
//                // right to left swipe
//                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.left_in));
//                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.left_out));                 
//                    viewFlipper.showNext();
//                    return true;
//                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.right_in));
//                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,R.anim.right_out));
//                    viewFlipper.showPrevious();
//                    return true;
//                }
// 
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
// 
//            return false;
//        }
//    }
	
	// Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent)
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen to swap
                         case MotionEvent.ACTION_DOWN:
                         {
                             lastX = touchevent.getX();
                             break;
                        }
                         case MotionEvent.ACTION_UP:
                         {
                             float currentX = touchevent.getX();
                            
                             // if left to right swipe on screen
                             if (lastX < currentX)
                             {
                                  // If no more View/Child to flip
                                 if (viewFlipper.getDisplayedChild() == 0)
                                     break;
                                
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Left and current Screen will go OUT from Right
                                 viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                 viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                 // Show the next Screen
                                 viewFlipper.showNext();
                             }
                            
                             // if right to left swipe on screen
                             if (lastX > currentX)
                             {
                                 if (viewFlipper.getDisplayedChild() == 1)
                                     break;
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Right and current Screen will go OUT from Left
                                 viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                 viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                                 // Show The Previous Screen
                                 viewFlipper.showPrevious();
                             }
                             break;
                         }
                 }
                 return false;
    }

}
