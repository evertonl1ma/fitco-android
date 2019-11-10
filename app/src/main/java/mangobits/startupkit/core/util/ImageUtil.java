package mangobits.startupkit.core.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

	
	public static void reduceImage(String pathOrigem, String nomeArquivoOrigem, String pathDestino, String nomeArquivoDestino, int resolucao) throws Exception {
		
		Bitmap bmOrigem = lerImagem(pathOrigem, nomeArquivoOrigem);

		saveImage(pathDestino, nomeArquivoDestino, bmOrigem, resolucao);
	}
	
	
	public static void saveImage(String path, String nomeArquivo, Bitmap dados) throws Exception {

		saveImage(path, nomeArquivo, dados, 100);
	
	}
	
	
	public static void saveImage(String path, String nomeArquivo, Bitmap dados, int resolucao) throws Exception {
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		dados.compress(CompressFormat.PNG, resolucao, stream);
		
		byte[] byteArray = stream.toByteArray();

		saveImage(path, nomeArquivo, byteArray);
	}
	
	
	public static void saveImage(String path, String nomeArquivo, byte[] dados) throws Exception {
		
		File sdCard = Environment.getExternalStorageDirectory();
		
		File dir = new File(sdCard.getAbsolutePath() + path);
		
		if(!dir.exists()){
			
			dir.mkdirs();
			
		}
		
		File file = new File(dir, nomeArquivo);

		FileOutputStream fos = new FileOutputStream(file);
		
		fos.write(dados);
		
		fos.flush();
		
		fos.close();
	}
	
	public static Bitmap lerImagem(String path, String nomeArquivo) {
		
		File sdCard = Environment.getExternalStorageDirectory();
		
		String pathArquivo = sdCard.getAbsolutePath() + path + "/" + nomeArquivo;
		
		Bitmap bitmap =  BitmapFactory.decodeFile(pathArquivo);
		
		return bitmap;
	}
	
	public static void removerImagem(String path, String nomeArquivo) throws Exception {
		
		File sdCard = Environment.getExternalStorageDirectory();
		
		File dir = new File(sdCard.getAbsolutePath() + path);
		
		dir.mkdirs();
		
		File file = new File(dir, nomeArquivo);

		file.delete();
	}
	
	public static byte[] conveterBitmapParaByteArray(Bitmap bitmap) {
	    
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    
		bitmap.compress(CompressFormat.PNG, 100, stream);
	    
		return stream.toByteArray();
	}
	
	
	public static String conveterBitmapParaBase64(Bitmap bitmap) {
	    
		String base64 = "";
		
		byte[] byteImagem =  conveterBitmapParaByteArray(bitmap);
	    
		if(byteImagem!=null){
			
			base64 = Base64.encodeToString(byteImagem, Base64.NO_WRAP);
			
		}
		
		return base64;
	}
	
	
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	
	
	
	public static String converteImagemBase64(Bitmap bitmap) {
		
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	    bitmap.compress(CompressFormat.PNG, 50, byteArrayOutputStream);

	    byte[] b = byteArrayOutputStream.toByteArray(); 

	    String encodedString = Base64.encodeToString(b, Base64.DEFAULT);

	    return encodedString;
	}
	
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}
	
	
	public static Bitmap lerImagemReduzida(String path, String nomeArquivo, int tamanhoMinimo) {

		File sdCard = Environment.getExternalStorageDirectory();
		
		String pathArquivo = null;
		
		if(path.indexOf("storage") != -1){
		
			pathArquivo = path + "/" + nomeArquivo;
		}
		else{
			
			pathArquivo = sdCard.getAbsolutePath() + path + "/" + nomeArquivo;
		}
		
		
		
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(pathArquivo, options);

	    int width = options.outWidth;
	    
	    int height = options.outHeight;
	    
	    int reqWidth = 0; 
	    
	    int reqHeight = 0;
	    
	    if(width > height){
	    	reqHeight = (height * tamanhoMinimo) / width;
	    	reqWidth = tamanhoMinimo;
	    }
	    else if(height > width){
	    	reqWidth = (width * tamanhoMinimo) / height;
	    	reqHeight = tamanhoMinimo;
	    }
	    else if(width == height){
	    	reqWidth = tamanhoMinimo;
	    	reqHeight = tamanhoMinimo;
	    }
	    
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    
		
		Bitmap bitmap = BitmapFactory.decodeFile(pathArquivo, options);
	    
		return bitmap;
	}
	
	//*
	public static Bitmap gerarBitmap(String path, Context context) throws Exception {
		
		byte[] byteArray = gerarDados(path, context);
		
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		
		return bitmap;
	}
	
	public static byte[] gerarDados(String path, Context context) throws Exception {
		
		String pathBase = context.getExternalFilesDir(null) + File.separator + path;
		int bytesread = 0;
		
		File file = new File(pathBase);
		
		byte[] byteArray = new byte[(int)file.length()];
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		int b = 0;
		while((b = bis.read()) != -1) {
			byteArray[bytesread++] = (byte) b;
		}
		
		return byteArray;
	}


	public static void writeImage(File file, Bitmap bitmap) throws Exception {

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
			// PNG is a lossless format, the compression factor (100) is ignored
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
}











