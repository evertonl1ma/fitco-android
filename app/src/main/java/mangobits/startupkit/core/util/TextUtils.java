package mangobits.startupkit.core.util;

import android.content.Context;
import android.text.util.Linkify;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by diegobellimondego on 30/03/16.
 */
public class TextUtils {


    public static void linkfy(TextView textView){

        final Pattern hashtagPattern = Pattern.compile("!([^ |\n])+([^!])");

        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return match.group();
            }
        };
        Linkify.addLinks(textView, hashtagPattern, "com.interesio.interesse.interesseactivity://", null,
                filter);


        final Pattern hashtagPatternWeb = Pattern.compile("http://([^ ])+");

        Linkify.TransformFilter filterWeb = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return match.group().replaceAll("http://", "");
            }
        };

        Linkify.addLinks(textView, hashtagPatternWeb, "http://", null, filterWeb);


        final Pattern hashtagPatternWebS = Pattern.compile("https://([^ ])+");

        Linkify.TransformFilter filterWebS = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return match.group().replaceAll("https://", "");
            }
        };

        Linkify.addLinks(textView, hashtagPatternWebS, "https://", null, filterWebS);
    }



    public static String formatarData(Context context, Date data){

        String formatado = null;

        DateFormat dfDate = android.text.format.DateFormat.getDateFormat(context);
        dfDate.setTimeZone(TimeZone.getDefault());

        DateFormat dfTime = android.text.format.DateFormat.getTimeFormat(context);
        dfTime.setTimeZone(TimeZone.getDefault());

        formatado = dfDate.format(data); //+ " " + dfTime.format(data);

        return formatado;
    }


    public static String formatarHora(Context context, Date data){

        String formatado = null;

        DateFormat dfTime = android.text.format.DateFormat.getTimeFormat(context);
        dfTime.setTimeZone(TimeZone.getDefault());

        formatado = dfTime.format(data);

        return formatado;
    }
}
