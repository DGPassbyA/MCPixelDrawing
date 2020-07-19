package PixelDrawing;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PixelImage {
    public static void resize(String inputImagePath,String outputImagePath,int targetWidth,int targetHeight){
        try{
            //读取输入文件
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);
            //创建目标图片
            BufferedImage outputImage = new BufferedImage(targetWidth,targetHeight,inputImage.getType());
            //调整大小
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage,0,0,targetWidth,targetHeight,null);
            g2d.dispose();
            //获取拓展名
            String formatName = outputImagePath.substring(outputImagePath.lastIndexOf('.')+1);
            //写入文件
            ImageIO.write(outputImage,formatName,new File(outputImagePath));
        }catch(IOException e){
            System.err.println("IOException : " + e);
            e.printStackTrace();
        }

    }
    public static void resize(String inputImagePath,String outputImagePath,double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        //计算比例下的宽高
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
    public static int[][][] getPixelMatrix(String filePath){
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            int width = image.getWidth();
            int height = image.getHeight();
            //三维矩阵行存储RGB信息
            int[][][] matrix = new int[height][width][3];
            //左上角为原点进行行扫描
            for(int i = 0;i < height; i++){
                for(int j = 0;j < width; j++){
                    //pixel为32位int，ARGB各占8位
                    int pixel = image.getRGB(j,i);
                    //分离RGB
                    matrix[i][j][0] = 0xff & (pixel >> 16);
                    matrix[i][j][1] = 0xff & (pixel >> 8);
                    matrix[i][j][2] = 0xff & pixel ;
                }
            }
            return matrix;
        }catch(IOException e){
            System.err.println("IOException : " + e);
            e.printStackTrace();
            return null;
        }
    }
}
