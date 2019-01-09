import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;


public class TableJob {

    public static void main(String[] args) throws Exception {
        BatchTableEnvironment tableEnv=null;


        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        tableEnv = BatchTableEnvironment.getTableEnvironment(env);

        //source,这里读取CSV文件，并转换为对应的Class
        DataSet<TopScorers> csvInput = env
                .readCsvFile("D:\\homework\\batch\\health.csv")
                .ignoreFirstLine().pojoType(TopScorers.class, "is_female", "age_at_study_date", "met_hours", "bmi_calc", "waist_kg","standing_height_cm",  "waist_cm","hip_cm");

        //将DataSet转换为Table
        Table topScore = tableEnv.fromDataSet(csvInput);
        //将topScore注册为一个表
        tableEnv.registerTable("topScore", topScore);

        int age=43;
        int sex=1;
            String maxsql="select max(standing_height_cm) as standing_height_cm,max(met_hours) as met_hours,max(hip_cm) as hip_cm,max(waist_kg) as waist_kg,max(bmi_calc) as bmi_calc from topScore where age_at_study_date>"+String.valueOf( age-2) +" and age_at_study_date<"+ String.valueOf( age+2)+" and is_female="+String.valueOf( sex);
            String minsql="select min(standing_height_cm) as standing_height_cm,min(met_hours) as met_hours,min(hip_cm) as hip_cm,min(waist_kg) as waist_kg,min(bmi_calc) as bmi_calc from topScore where age_at_study_date>"+String.valueOf( age-2) +" and age_at_study_date<"+ String.valueOf( age+2)+" and is_female="+String.valueOf( sex);
            Table groupedByCountry=null;

            System.out.println(maxsql);
//            if(getmax==1)
//              groupedByCountry= tableEnv.sql(maxsql);
//            else
                groupedByCountry= tableEnv.sql(minsql);

            //转换回dataset
            DataSet<Result> result = tableEnv.toDataSet(groupedByCountry,Result.class);
            //将dataset map成tuple输出
            for(int i=0;i<1;i++) {
                try{
                List<Tuple5<Double, Double, Double, Double, Double>> tempe = result.map(new MapFunction<Result, Tuple5<Double, Double, Double, Double, Double>>() {
                    public Tuple5<Double, Double, Double, Double, Double> map(Result result) throws Exception {
                        Double standing_height_cm = result.standing_height_cm;
                        Double met_hours = result.met_hours;
                        Double hip_cm = result.hip_cm;
                        Double waist_kg = result.waist_kg;
                        Double bmi_calc = result.bmi_calc;

                        return Tuple5.of(standing_height_cm, met_hours, hip_cm, waist_kg, bmi_calc);
                    }
                }).collect();
                Iterator var2 = tempe.iterator();
                while(var2.hasNext()) {
                    String getstring=var2.next().toString();
                    System.out.println(getstring);
//                    return getstring;
                }
                }
                catch(Exception e)
                {

                }


            }
//            return new String();
    }



//        Iterator var2 = elements.iterator();
//        while(var2.hasNext()) {
//            System.out.println(var2.next());
//
//        }

//        System.out.println(r);
//        System.out.println(result.max(0).toString());

//        DataSet<Result> r2 =result.max(0);

    /**
     * 源数据的映射类
     */
    public static class TopScorers {
        /**
         * 排名，球员，国籍，俱乐部，总进球，主场进球数，客场进球数，点球进球数
         */
        public Double bmi_calc;
        public int age_at_study_date;
        public Double waist_kg;
        public Double met_hours;
        public Double standing_height_cm;
        public Double waist_cm;
        public int is_female;
        public Double hip_cm;

        public int rank;
        public String player;
        public String country;
        public String club;
        public int total_score;
        public int total_score_home;
        public int total_score_visit;
        public int point_kick;

        public TopScorers() {
            super();
        }
    }

    /**
     * 统计结果对应的类
     */
    public static class Result {
        public double bmi_calc;
        public double waist_kg;
        public double met_hours;
        public double standing_height_cm;
        public double hip_cm;

        public Result() {}
    }

//    public static void main(String[] args) {
//        try {
//            TableJob s = new TableJob();
//            s.SearchEvent(1,34,1);
//        }
//        catch (Exception e)
//        {
//
//        }
//    }
}
