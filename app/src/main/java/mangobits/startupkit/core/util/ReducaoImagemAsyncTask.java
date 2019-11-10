package mangobits.startupkit.core.util;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;



public class ReducaoImagemAsyncTask extends AsyncTask<File, String, Bitmap> {
	
	
	private Context context;
	
//	private ProgressDialog progress;
	
	private Fragment fragment;
	
	private final WeakReference<ImageView> imageViewReference;
	
	private final int TAMANHO_MAXIMO = 900;
	
	private boolean avatar;
	
	private Bitmap bitmap;

	private ReducaoImagemPostListener listener;


	public ReducaoImagemAsyncTask(Context context, ImageView imageView){

		this.context = context;

		this.imageViewReference = new WeakReference<ImageView>(imageView);
	}



	public ReducaoImagemAsyncTask(Context context, ImageView imageView, ReducaoImagemPostListener listener){

		this.context = context;

		this.imageViewReference = new WeakReference<ImageView>(imageView);

		this.listener = listener;
	}


//	public ReducaoImagemAsyncTask(Context context, ImageView imageView, Bitmap bitmap){
//
//		this.context = context;
//
//		this.imageViewReference = new WeakReference<ImageView>(imageView);
//
//		this.bitmap = bitmap;
//	}



	public ReducaoImagemAsyncTask(Context context, ImageView imageView, Fragment fragment){

		this.context = context;

		this.imageViewReference = new WeakReference<ImageView>(imageView);

		this.fragment = fragment;

	}


	@Override
	protected void onPreExecute() {

//        progress = new ProgressDialog(context);
//
//        progress.setCanceledOnTouchOutside(false);
//
//        progress.setMessage("");
//
//        try {
//        	progress.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}



	@Override
	protected Bitmap doInBackground(File... arquivo) {

		Bitmap bitmapRet = null;
//		PurpleBag.getApplicationBag().setAttribute("updateAvatar", true);

//		int tamanhoMinimo = arquivo.length >= 3 ? Integer.parseInt(arquivo[2]) : TAMANHO_MAXIMO;
		int tamanhoMinimo = TAMANHO_MAXIMO;

		int larguraInicial = 0;
		int alturaInicial = 0;

		try {

//			String path = null;

			String nomeImagem = null;

			if(bitmap == null){

				bitmap = ImagemUtil.lerImagemReduzida(arquivo[0], tamanhoMinimo);

				larguraInicial = bitmap.getWidth();
				alturaInicial = bitmap.getHeight();

				bitmapRet = bitmap;

				if(larguraInicial == alturaInicial){
					//quadrado
					avatar = true;
				}
			}
			else{

				larguraInicial = bitmap.getWidth();
				alturaInicial = bitmap.getHeight();
				int larguraFinal = 0;
				int alturaFinal = 0;
				boolean reduzir = true;

				if(larguraInicial > alturaInicial && larguraInicial > tamanhoMinimo){
					//paisagem
					larguraFinal = tamanhoMinimo;
					alturaFinal = (tamanhoMinimo * alturaInicial) / larguraInicial;
				}
				else if(alturaInicial > larguraInicial && alturaInicial > tamanhoMinimo){
					//retrato
					alturaFinal = tamanhoMinimo;
					larguraFinal = (tamanhoMinimo * larguraInicial) / alturaInicial;
				}
				else if(larguraInicial == alturaInicial){
					//quadrado
					alturaFinal = tamanhoMinimo;
					larguraFinal = tamanhoMinimo;
					avatar = true;
				}
				else{
					reduzir = false;
				}

				if(reduzir){

					Bitmap red = Bitmap.createScaledBitmap(bitmap, larguraFinal, alturaFinal, true);
					bitmapRet = red;
				}
			}


			File sdCard = Environment.getExternalStorageDirectory();
			String pathArquivo = null;

			if(arquivo[0].getPath().indexOf("storage") != -1){

				pathArquivo = arquivo[0].getPath();
			}
			else{

				pathArquivo = sdCard.getAbsolutePath() + arquivo[0].getPath();
			}


			ExifInterface exifDataReader = new ExifInterface(pathArquivo);
			int orientation = exifDataReader.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


			if((alturaInicial > larguraInicial && orientation == ExifInterface.ORIENTATION_ROTATE_180)
					|| (larguraInicial > alturaInicial && (orientation == ExifInterface.ORIENTATION_ROTATE_90 || orientation == ExifInterface.ORIENTATION_ROTATE_270))){


				int rotate = 0;

				switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_270:
						rotate = 270;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						rotate = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_90:
						rotate = 90;
						break;
				}

				Matrix matrix = new Matrix();
				matrix.postRotate(rotate);
				bitmapRet = Bitmap.createBitmap(bitmapRet , 0, 0, bitmapRet.getWidth(), bitmapRet.getHeight(), matrix, true);

			}


			try {
//				ImagemUtil.removerImagem(arquivo[0].getPath(), nomeImagem);
			} catch (Exception e) {
				//nao ha imagem no disco
			}

			bitmap = null;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return bitmapRet;
	}


	@Override
	protected void onProgressUpdate(String... values) {
//		progress.setMessage(values[0]);
	}


	@Override
	protected void onPostExecute(Bitmap bitmap) {

		if (isCancelled()) {
	           bitmap = null;
		}

		if (imageViewReference != null) {

			ImageView imageView = imageViewReference.get();

			if (imageView != null && bitmap!=null) {

				//diego
//				if(avatar){
//					bitmap = ImagemUtil.getRoundedCornerBitmap(bitmap, 100);
//				}

				imageView.setImageBitmap(bitmap);

				if(listener != null){
					listener.executar();
				}
			}
		}


		if(context instanceof NotificacaoTask){
			((NotificacaoTask)context).tarefaFinalizada();
		}
		else if((fragment != null && fragment instanceof NotificacaoTask)){
			((NotificacaoTask)fragment).tarefaFinalizada();
		}
		
//		progress.dismiss();
	}
}
