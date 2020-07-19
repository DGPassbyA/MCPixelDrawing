package PixelDrawing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Converter {
    //创建空文件
    private static void creatFile(String outputPath,boolean replace) {
        //新建文件
        String path = "";
        String name = "";
        if(outputPath.contains("/")){
            path = outputPath.substring(0,outputPath.lastIndexOf('/'));
            name = outputPath.substring(outputPath.lastIndexOf('/')+1);
        }else{
            path = "./";
            name = outputPath;
        }
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File file = new File(path,name);
        try{
            if(file.exists() && replace){
                System.out.println("Warning：文件已被替换！");
                file.delete();
            }
            file.createNewFile();
        }catch (IOException e){
            System.err.println("IOException : " + e);
            e.printStackTrace();
        }
    }
    //写入文件
    public static void writeFile(String outputPath,Block[] list,int[][] result,String towards){
        int height = result.length;
        int width = result[0].length;
        try{
            File file = new File(outputPath);
            creatFile(outputPath,true);
            FileWriter filewriter = new FileWriter(file.getName(),true);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            for(int i = height - 1;i > 0 ;i--){
                for(int j = width -1 ;j > 0;j--){
                    if(towards.equals("z")){
                        bufferedwriter.write("setblock ~ ~" + (height - i) + " ~-" + j + " " + list[result[i][j]].blockName + "\n\r");
                    }else if(towards.equals("x")) {
                        bufferedwriter.write("setblock ~-" + j + " ~" + (height - i) + " ~ " + list[result[i][j]].blockName + "\n\r");
                    }
                }

            }
            bufferedwriter.close();
        }catch(IOException e){
            System.err.println("IOException : " + e);
            e.printStackTrace();
        }
    }
    //对比颜色，返回最接近的色盘颜色下标
    private static int compareColor(Block[] list,int[] RGB){
        if(list.length <= 0) {
            System.out.println("Warning：方块列表为空！");
            return -1;
        }
        int R = RGB[0];
        int G = RGB[1];
        int B = RGB[2];
        int index = 0;
        double min = Math.sqrt((list[0].R - R)*(list[0].R - R) + (list[0].G - G)*(list[0].G - G) + (list[0].B - B)*(list[0].B - B));
        double temp;
        for(int i = 1;i < list.length;i++){
            if((temp = Math.sqrt((list[i].R - R)*(list[i].R - R) + (list[i].G - G)*(list[i].G - G) + (list[i].B - B)*(list[i].B - B))) < min){
                min = temp;
                index = i;
            }
        }
        return index;
    }
    //对比得到整张图的转换表
    public static int[][] getResultColor(Block[] list,int[][][] matrix){
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] result = new int[height][width];
        for(int i = 0;i < height;i++){
            for(int j = 0;j < width;j++){
                result[i][j] = compareColor(list,matrix[i][j]);
            }
        }
        return result;
    }
    public static void main(String[] args) throws IOException {
        String imagePath_1 = args[0];
        String percent = args[1];
        String towards = args[2];
        String outputFilePath = args[3];
        if(towards.equals("x") || towards.equals("z")){
            String imagePath_2 = "./resize.jpg";
            String jsonPath = "./mc_block_list.json";
            PixelImage.resize(imagePath_1,imagePath_2,Double.parseDouble(percent));
            Block[] list = JSONReader.strToList(JSONReader.readJSON(jsonPath));
            int[][][] matrix = PixelImage.getPixelMatrix(imagePath_2);
            if(matrix != null){
                System.out.println("Converting...");
                int[][] result = getResultColor(list,matrix);
                writeFile(outputFilePath,list,result,towards);
            }
            System.out.println("Write into file finish！");
            File resizeImg = new File(imagePath_2);
            if(resizeImg.exists()){
                resizeImg.delete();
            }
        }else{
            System.out.println("Warning：方向参数只支持x和z！");
            System.exit(1);
        }
    }
}
