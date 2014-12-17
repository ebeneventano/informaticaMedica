import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Histograma {
    
    /**
     * Calculamos la media de una variable Color
     * @param color Color del cual se quiere obtener la media
     * @return entero con el valor de la media
     */
    private int calcularMedia(Color color){
        int mediaColor;
        mediaColor=(int)((color.getRed()+color.getGreen()+color.getBlue())/3);
        return mediaColor;
    }
    
    /**
     * Devuelve el histograma de la imagen.
     * @param imagen BufferedImagen de la cual se quiere obtener el histograma
     * @return Devuelve una variable int[5][256], donde el primer campo[0] corresponde
     * al canal Rojo, [1]=verde [2]=azul [3]=alfa [4]=escala grises
     */
    public int[][] completarHistograma(BufferedImage imagen){
        Color colorAuxiliar;
        int histogramaReturn[][]=new int[5][256];
        //Recorremos la imagen
        for( int i = 0; i < imagen.getWidth(); i++ ){
            for( int j = 0; j < imagen.getHeight(); j++ ){
                //Obtenemos color del pixel actual
                colorAuxiliar=new Color(imagen.getRGB(i, j));
                histogramaReturn[0][colorAuxiliar.getRed()]+=1;
                histogramaReturn[1][colorAuxiliar.getGreen()]+=1;
                histogramaReturn[2][colorAuxiliar.getBlue()]+=1;
                histogramaReturn[3][colorAuxiliar.getAlpha()]+=1;
                histogramaReturn[4][calcularMedia(colorAuxiliar)]+=1;
            }
        }
        return histogramaReturn;
    }
    
    public static String parsearHistograma(int[][] histogramaArray) {
		String array = "{";
		
		for(int i = 0; i<3; i++){
			array += Arrays.toString(histogramaArray[i]).replace("[", "{").replace("]", "}");
			if(i<2){
				array+=",";
			}
		}
		
		array += "}";
		
		return array;
	}
    
}
