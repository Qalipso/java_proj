package mypack;
public class ChangeStr {
    public String modE(String str,int modifyE){
        StringBuilder tmpStr = new StringBuilder();
        if (modifyE == 0){
            return str;
        }
        if (modifyE == 1){
            for (int i =0; i<str.length();i++){
                if (str.charAt(i) == 'э'){
                    tmpStr.append("&э");
                }
                else
                if (str.charAt(i) == 'е'){
                    tmpStr.append("э");
                }
                else{
                    tmpStr.append(str.charAt(i));
                }
            }
        }
        if (modifyE == 2){
            tmpStr.append(str.charAt(1));
            for (int i =2; i<str.length();i++){
                if (str.charAt(i) == 'э'){
                    tmpStr.append("&э");
                }
                else
                if ((str.charAt(i) == 'е')&&((str.charAt(i-1) == ' ')||(str.charAt(i-1) == '-')||(str.charAt(i-1) == '\''))){
                    tmpStr.append("e");
                }
                else if((str.charAt(i) == 'е')){
                    tmpStr.append("э");

                }
                else{
                    tmpStr.append(str.charAt(i));
                }
            }
        }
        return tmpStr.toString();
    }
    public String modU(String str){
        StringBuilder tmpStr = new StringBuilder();
            //tmpStr.append(str.charAt(0));
            for (int i = 0;i<str.length();i++){
                if (i <str.length()-1 && (str.charAt(i) == 'й')&&((str.charAt(i+1) == 'а')||(str.charAt(i+1) == 'у')||(str.charAt(i+1) == 'о'))){
                    tmpStr.append("й$");
                }
                else
                if (i <str.length()-1 && (str.charAt(i) == 'Й')&&((str.charAt(i+1) == 'А')||(str.charAt(i+1) == 'У')||(str.charAt(i+1) == 'О'))){
                    tmpStr.append("Й$");
                }
                else
                if (str.charAt(i) == 'я'){
                    tmpStr.append("йа");
                }
                else
                if (str.charAt(i) == 'Я'){
                    tmpStr.append("ЙА");
                }
                else
                if (str.charAt(i) == 'ю'){
                    tmpStr.append("йу");
                }
                else
                if (str.charAt(i) == 'Ю'){
                    tmpStr.append("ЙУ");
                }
                else
                if (str.charAt(i) == 'ё'){
                    tmpStr.append("йо");
                }
                else
                if (str.charAt(i) == 'Ё'){
                    tmpStr.append("ЙО");
                }
                else
                {
                    tmpStr.append(str.charAt(i));
                }
            }
        return tmpStr.toString();
    }

    public String modZi(String str){
        StringBuilder tmpStr = new StringBuilder();
        tmpStr.append(str.charAt(0));
        for (int i = 1;i<str.length();i++) {
            if ((str.charAt(i-1) == 'ц') && (str.charAt(i) == 'ы'))  {
                tmpStr.append("&и");
            }
            else{
                tmpStr.append(str.charAt(i));
            }
        }
        return tmpStr.toString();
    }
}




