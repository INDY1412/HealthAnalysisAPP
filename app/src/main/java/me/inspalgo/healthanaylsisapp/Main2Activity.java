package me.inspalgo.healthanaylsisapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    PieChart mPieChart;
    RadarChart mRadarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mPieChart = findViewById(R.id.pieChart);
        setPieChart();

        mRadarChart = findViewById(R.id.radarChart);
        setRadarChart();
    }

    /**
     * 环形饼图
     */

//    private SpannableString generateCenterSpannableText() {
//        SpannableString s = new SpannableString("中间文字");
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 9, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 9, s.length() - 10, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 9, s.length() - 10, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 9, s.length() - 10, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 9, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 9, s.length(), 0);
//        return s;
//    }
    private void setPieChart() {
        mPieChart.setUsePercentValues(true);  // 当前值显示成百分比，如果不设置这个属性pieData.setValueFormatter设置显示的是错误的
        mPieChart.getDescription().setEnabled(false);  // 右下角description不显示
        // TODO 设置中间文字有线程问题，故删去
        //mPieChart.setCenterText(generateCenterSpannableText());  // 设置中间文字
        mPieChart.setDrawHoleEnabled(true);  // 是否显示成同心圆的形式
        mPieChart.setHoleColor(Color.WHITE);  // 同心圆的圆心颜色
        mPieChart.setTransparentCircleColor(Color.WHITE);  // 设置同心圆的中心圆和外圈之间的颜色
        mPieChart.setTransparentCircleAlpha(110);  // 设置同心圆的中心圆和外圈之间的颜色透明度
        mPieChart.setDrawCenterText(false);  // 是否显示中间的文字
        mPieChart.setRotationAngle(0);  //  饼图旋转角度
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);  // 动画

        Legend legend = mPieChart.getLegend();  // legend的设置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);

        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight h) {
                // toastUI(entry.getY() + "");
            }

            @Override
            public void onNothingSelected() {
                // toastUI("nothing");
            }
        });

        setPieChartData();
    }

    private void setPieChartData() {

        String[] mIntro = {"yi", "er", "san", "si", "wu", "liu", "qi", "ba", "jiu"};
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the
        // center of
        // the chart.
        for (int i = 0; i < mIntro.length; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 100) + 100 / 5),
                    mIntro[i % mIntro.length],
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);  // 不显示实体类中的图片
        dataSet.setSliceSpace(3f);  // 每一个部分分割空白宽度
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());  // 值添加百分比
        data.setValueTextSize(11f);  // 值的字体大小
        data.setValueTextColor(Color.WHITE);  // 值的字体颜色

        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }


    /**
     * 雷达图
     */

    private void setRadarChart() {
        //mRadarChart.setBackgroundColor(Color.WHITE);  // 设置图表的背景颜色

        mRadarChart.getDescription().setEnabled(false);  // 设置右下角的description不显示

        mRadarChart.setWebLineWidth(2f);  // 设置外层每个点到中心点的线的宽度（y轴） 设置每个角到中心的线的宽度
        mRadarChart.setWebColor(Color.rgb(186, 185, 186));  // 设置外层每个点到中心点的线的颜色（y轴） 设置每个角到中心的线的颜色
        mRadarChart.setWebLineWidthInner(1f);  // 设置外层的线的宽度（x轴） 设置外圈的线的宽度
        mRadarChart.setWebColorInner(Color.rgb(204, 202, 204));  // 设置外层的线的颜色（x轴） 设置外圈的线的颜色
        mRadarChart.setWebAlpha(180);  // 设置外层所有线的颜色的透明度（y轴和x轴）设置外圈的线的透明度（包括每个角到中心的线和外圈的线）

        // 设置点击的 markerView
        // TODO 有线程问题，故删去
//        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
//        mv.setChartView(mRadarChart);  // For bounds control
//        mRadarChart.setMarker(mv);  // Set the marker to the chart

        setRadarChartData();

        mRadarChart.animateXY(1400, 1400,  // 设置显示动画
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        // 设置x轴的相关属性 设置外层的文字等相关属性
        XAxis xAxis = mRadarChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setXOffset(0f);
        xAxis.setYOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private String[] mActivities = new String[]{
                    "Burger", "Steak", "Salad", "Pasta", "Pizza"
            };

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.BLACK);

        // 设置y轴的相关属性 //设置外层点到中间点的文字属性
        YAxis yAxis = mRadarChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);  // 层级标签是否显示

        Legend I = mRadarChart.getLegend();
        I.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        I.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        I.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        I.setDrawInside(false);
        I.setXEntrySpace(7f);
        I.setYEntrySpace(5f);
        I.setTextColor(Color.BLACK);
    }

    private void setRadarChartData() {
        float mul = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(174, 235, 112));
        set1.setFillColor(Color.rgb(210, 235, 194));
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);
        set1.setFillAlpha(120);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(235, 112, 128));
        set2.setFillColor(Color.rgb(235, 199, 202));
        set2.setDrawFilled(true);
        set2.setFillAlpha(120);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mRadarChart.setData(data);
        mRadarChart.invalidate();
    }
}
