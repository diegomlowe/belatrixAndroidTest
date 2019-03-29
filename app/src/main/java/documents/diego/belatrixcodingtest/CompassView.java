package documents.diego.belatrixcodingtest;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CompassView  extends View {

    private Paint mPaintCompass;
    private Paint pTriangleBlack;
    private Paint pTriangleWhite;
    private Paint textPaint;
    private Paint anglePaint;

    private int angleVariation=20;


    // PADDING TO PIXELS
    int textPadding = 5;
    int pixelPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            textPadding, getResources().getDisplayMetrics());

    int textSize=15;
    int pixelTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            textSize, getResources().getDisplayMetrics());

    private int color;

    private int x, y, radius; // COORDENADAS DEL CENTRO Y RADIO


    public CompassView(Context context) {
        super(context);

        init (null);
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set){

        mPaintCompass = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCompass.setColor(getResources().getColor(R.color.compass_color));

        pTriangleBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        pTriangleBlack.setColor(Color.BLACK);

        pTriangleWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        pTriangleWhite.setColor(Color.WHITE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(pixelTextSize);

        anglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        anglePaint.setTextSize(pixelTextSize/2);
        anglePaint.setColor(Color.WHITE);


    }

    @Override
    protected void onDraw(Canvas canvas) {


        x = (int) getWidth()/2;
        y = (int) getHeight()/2;
        radius = (int) getWidth()/2-100;

        canvas.drawCircle(x, y, radius, mPaintCompass);

        int z = (int)radius/8;

        Point center = new Point (x, y);

        Point north = new Point(x, y-radius);
        Point east = new Point (x+radius, y);
        Point west = new Point (x-radius, y);
        Point south = new Point (x, y+radius);

        Point ne = new Point (x+z, y-z);
        Point nw = new Point (x-z, y-z);
        Point se = new Point (x+z, y+z);
        Point sw = new Point (x-z, y+z);


        // NORTE
        canvas.drawText("N", x-pixelTextSize/3, y-radius-pixelPadding, textPaint);
        drawTriangle(canvas, center, north, ne, pTriangleBlack);
        drawTriangle(canvas, center, north, nw, pTriangleWhite);

        // SUR
        canvas.drawText("S", x-pixelTextSize/3, y+radius+pixelTextSize+pixelPadding, textPaint);
        drawTriangle(canvas, center, south, sw, pTriangleBlack);
        drawTriangle(canvas, center, south, se, pTriangleWhite);

        // ESTE
        canvas.drawText("E", x+radius+pixelPadding, y+pixelTextSize/3, textPaint);
        drawTriangle(canvas, center, east, se, pTriangleBlack);
        drawTriangle(canvas, center, east, ne, pTriangleWhite);

        // OESTE
        canvas.drawText("W", x-radius-pixelTextSize-pixelPadding, y+pixelTextSize/3, textPaint);
        drawTriangle(canvas, center, west, nw, pTriangleBlack);
        drawTriangle(canvas, center, west, sw, pTriangleWhite);

        printAngles(canvas, center, (int)radius);


    }

    public void printAngles(Canvas canvas, Point center, int radio){

        double x;
        double y;
        int xCenter = center.x;
        int yCenter= center.y;

        for(int i =0; i<360; i+=angleVariation){

           if(i!=0 && i!=90 && i!= 180 && i!=270) {

                // POSICION DE CADA ANGULO RESPECTO AL RADIO:

                double iRadianes;

                iRadianes = Math.toRadians(i);

                x = radio * Math.sin(iRadianes) + xCenter;

                y = -1 * radio * Math.cos(iRadianes) + yCenter;


              // PADDING DE CADA CUADRANTE:

                if (i < 90) {

                    x += pixelPadding;
                    y -= pixelPadding;

                }
                if (i > 90 && i < 180) {

                    x += pixelPadding;
                    y += pixelPadding*2;

                } else if (i > 180 && i < 270) {

                    x -= pixelPadding*3;
                    y += pixelPadding*2;

                } else if (i > 270 && i < 360) {

                    x -= pixelPadding*3;
                    y -= pixelPadding;

                }

                canvas.drawText(String.valueOf(i), (int) x, (int) y, anglePaint);
            }

        }

    }

    public void drawTriangle(Canvas canvas, Point a, Point b, Point c, Paint paint){

        Path path = new Path();

        path.setFillType(Path.FillType.EVEN_ODD);

        path.lineTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.close();

        canvas.drawPath(path, paint);

    }

}
