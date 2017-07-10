package com.galebo.test.basic;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @see http://wiseideal.iteye.com/blog/1469295
 * @author Administrator
 *
 */
public class TestSoftReference {
	class Bitmap{
		
	}
	static class BitmapFactory{

		public static Bitmap decodeStream(InputStream inputStream) {
			return null;
		}
		
	}
	void dd(InputStream inputStream){
		/*Java中的SoftReference
		即对象的软引用。如果一个对象具有软引用，内存空间足够，垃 圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。软引用可用来实现内存敏感的高 速缓存。使用软引用能防止内存泄露，增强程序的健壮性。   
		SoftReference的特点是它的一个实例保存对一个Java对象的软引用， 该软引用的存在不妨碍垃圾收集线程对该Java对象的回收。也就是说，一旦SoftReference保存了对一个Java对象的软引用后，在垃圾线程对 这个Java对象回收前，SoftReference类所提供的get()方法返回Java对象的强引用。另外，一旦垃圾线程回收该Java对象之 后，get()方法将返回null

		用Map集合缓存软引用的Bitmap对象*/

		
		
		Map<String, SoftReference<Bitmap>> imageCache = new  HashMap<String, SoftReference<Bitmap>>();
		//强引用的Bitmap对象
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		//软引用的Bitmap对象
		SoftReference<Bitmap> softRbitmap = new SoftReference<Bitmap>(bitmap);
		//添加该对象到Map中使其缓存
		imageCache.put("1",softRbitmap);
		

		//从缓存中取软引用的Bitmap对象
		SoftReference<Bitmap> bitmapcache_ = imageCache.get("1");
		//取出Bitmap对象，如果由于内存不足Bitmap被回收，将取得空

		Bitmap bitmap_ = bitmapcache_.get();
		System.out.println(bitmap_);
		//如果程序中需要从网上加载大量的图片 这时就考虑采用在sdcard上建立临时文件夹缓存这些图片了
	}
	public static void main(String[] args) {
		
	}
}
