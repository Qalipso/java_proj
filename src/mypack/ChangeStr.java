package mypack;

public class ChangeStr {
    public String modSpecial(String str){
        StringBuilder tmpStr = new StringBuilder();
        for (int i =0;i<str.length();i++) {
            if (str.charAt(i) == '&'){
                tmpStr.append("$&");
            }
            else
            if (str.charAt(i) == '$'){
                tmpStr.append("$$");
            }
            else if (str.charAt(i) == '!' && str.charAt(i+1) != ' ' && str.charAt(i+1) != '?' && str.charAt(i+1) != '!' && str.charAt(i+1) != '.') {
                tmpStr.append("! ");
            }
            else if (str.charAt(i) == '?' && str.charAt(i+1) != ' ' && str.charAt(i+1) != '?' && str.charAt(i+1) != '!' && str.charAt(i+1) != '.') {
                tmpStr.append("? ");
            }
            else if (str.charAt(i) == ',' && str.charAt(i+1) != ' ') {
                tmpStr.append(", ");
            }
            else if (str.charAt(i) == '.' && str.charAt(i+1) == '.') {
                tmpStr.append(".");
            }
            else if (str.charAt(i) == '.' && str.charAt(i+1) != ' ') {
                tmpStr.append(". ");
            }

            else if (str.charAt(i) == ':' && str.charAt(i+1) != ' ') {
                tmpStr.append(": ");
            }
            else if (str.charAt(i) == ';' && str.charAt(i+1) != ' ') {
                tmpStr.append("; ");
            }
            else if (str.charAt(i) == '\t' && str.charAt(i+1) != ' ') {
                tmpStr.append("\t ");
            }
            else if (str.charAt(i) == (char)0x00A0 && str.charAt(i+1) != ' ') {
                tmpStr.append(" ");
            }
            else if (str.charAt(i) == (char)0x2007 && str.charAt(i+1) != ' ' ) {
                tmpStr.append(" ");
            }
            else if (str.charAt(i) == (char)0x202F && str.charAt(i+1) != ' ' ) {
                tmpStr.append(" ");
            }
            else if (str.charAt(i) == (char)0x2060 && str.charAt(i+1) != ' ' ) {
                tmpStr.append(" ");
            }
            else
                tmpStr.append(str.charAt(i));

        }
        tmpStr.append(str.charAt(str.length()-1));
return tmpStr.toString();
    }
    public String modE(String str, short modifyE) {
        StringBuilder tmpStr = new StringBuilder();
        if (modifyE == 0) {
            return str;
        }
        if (modifyE == 1) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == 'э') {
                    tmpStr.append("&э");
                } else if (str.charAt(i) == 'е') {
                    tmpStr.append("э");
                } else if (str.charAt(i) == 'Э') {
                    tmpStr.append("&Э");
                } else if (str.charAt(i) == 'Е') {
                    tmpStr.append("Э");
                } else {
                    tmpStr.append(str.charAt(i));
                }
            }
        }
        if (modifyE == 2) {
            tmpStr.append(str.charAt(0));
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i) == 'э') {
                    tmpStr.append("&э");
                } else if ((str.charAt(i) == 'е') && ((str.charAt(i - 1) == ' ') || (str.charAt(i - 1) == '-') || (str.charAt(i - 1) == '\''))) {
                    tmpStr.append("e");
                } else if ((str.charAt(i) == 'е')) {
                    tmpStr.append("э");

                } else {
                    tmpStr.append(str.charAt(i));
                }
            }
        }
        return tmpStr.toString();
    }

    public String modU(String str, short modifyU) {
        StringBuilder tmpStr = new StringBuilder();
        //tmpStr.append(str.charAt(0));
        if (modifyU == 1) {
            for (int i = 0; i < str.length(); i++) {
                if (i < str.length() - 1 && (str.charAt(i) == 'й') && ((str.charAt(i + 1) == 'а') || (str.charAt(i + 1) == 'у') || (str.charAt(i + 1) == 'о'))) {
                    tmpStr.append("й$");
                } else if (i < str.length() - 1 && (str.charAt(i) == 'Й') && ((str.charAt(i + 1) == 'А') || (str.charAt(i + 1) == 'У') || (str.charAt(i + 1) == 'О'))) {
                    tmpStr.append("Й$");
                } else if (str.charAt(i) == 'я') {
                    tmpStr.append("йа");
                } else if (str.charAt(i) == 'Я') {
                    tmpStr.append("ЙА");
                } else if (str.charAt(i) == 'ю') {
                    tmpStr.append("йу");
                } else if (str.charAt(i) == 'Ю') {
                    tmpStr.append("ЙУ");
                } else if (str.charAt(i) == 'ё') {
                    tmpStr.append("йо");
                } else if (str.charAt(i) == 'Ё') {
                    tmpStr.append("ЙО");
                } else {
                    tmpStr.append(str.charAt(i));
                }
            }
        }
        else if (modifyU == 2){
//            for (int i = 0; i < str.length(); i++) {
//                if (str.charAt(i) == 'й') {
//                    tmpStr.append("&й");
//                } else if (str.charAt(i) == 'Й') {
//                    tmpStr.append("&Й");
//                } else if (i >0 && ((str.toLowerCase().charAt(i-1) == 'й')||(str.toLowerCase().charAt(i-1) == 'ь')) &&
//                        ((str.toLowerCase().charAt(i ) == 'а') || (str.toLowerCase().charAt(i) == 'у') || (str.toLowerCase().charAt(i) == 'о'))) {
//                    tmpStr.append("$");
//                    tmpStr.append(str.charAt(i));
//                }  else if (str.charAt(i) == 'я') {
//                    tmpStr.append("йа");
//                }
//                else if (str.charAt(i) == 'Я') {
//                    tmpStr.append("ЙА");
//                } else if (str.charAt(i) == 'ю') {
//                    tmpStr.append("йу");
//                } else if (str.charAt(i) == 'Ю') {
//                    tmpStr.append("ЙУ");
//                } else if (str.charAt(i) == 'ё') {
//                    tmpStr.append("йо");
//                } else if (str.charAt(i) == 'Ё') {
//                    tmpStr.append("ЙО");
//                } else if (isGlas(tmpStr.charAt(tmpStr.length()-1)) && (i+1) < str.length() && str.charAt(i+1) == 'ё' ) {
//                    tmpStr.append(str.charAt(i));
//                    tmpStr.append("&йо");
//                    i++;
//                }
//                else if (isGlas(tmpStr.charAt(tmpStr.length()-1)) && (i+1) < str.length() && str.charAt(i+1) == 'ю' ) {
////                    if (str.charAt(i) == 'я')
////                        tmpStr.append("йа");
////                    else
////                    if (str.charAt(i) == 'ё')
////                        tmpStr.append("йо");
////                    else
////                    if (str.charAt(i) == 'ю')
////                        tmpStr.append("йу");
////                    else
////                    if (str.charAt(i) == 'Я')
////                        tmpStr.append("ЙА");
////                    else
////                    if (str.charAt(i) == 'Ю')
////                        tmpStr.append("ЙУ");
////                    else
////                    if (str.charAt(i) == 'Ё')
////                        tmpStr.append("ЙО");
////                    else
//                        tmpStr.append(str.charAt(i));
//                    tmpStr.append("&йу");
//                    i++;
//                }
//                else if (isGlas(tmpStr.charAt(tmpStr.length()-1)) && (i+1) < str.length() && str.charAt(i+1) == 'я' ) {
//                    tmpStr.append(str.charAt(i));
//                    tmpStr.append("&йа");
//                    i++;
//                }
//                else if (isGlas(tmpStr.charAt(tmpStr.length()-1)) && (i+1) < str.length() && str.charAt(i+1) == 'Ё' ) {
//                    tmpStr.append(str.charAt(i));
//                    tmpStr.append("&ЙО");
//                    i++;
//                }
//                else if (isGlas(tmpStr.charAt(tmpStr.length()-1)) && (i+1) < str.length() && str.charAt(i+1) == 'Ю' ) {
//                    tmpStr.append(str.charAt(i));
//                    tmpStr.append("&ЙУ");
//                    i++;
//                }
//                else if (isGlas(tmpStr.charAt(tmpStr.length()-1)) && (i+1) < str.length() && str.charAt(i+1) == 'Я' ) {
//                    tmpStr.append(str.charAt(i));
//                    tmpStr.append("&ЙА");
//                    i++;
//                }
//                else if (str.charAt(i) == 'ь'){
//                    tmpStr.append("й");
//                } else if (str.charAt(i) == 'Ь'){
//                    tmpStr.append("Й");
//                }
//                else tmpStr.append(str.charAt(i));
//            }
//            System.out.println(tmpStr.toString());
            if (str.charAt(0) == 'ь'){
                tmpStr.append("й");
            } else if (str.charAt(0) == 'Ь'){
                tmpStr.append("Й");
            } else if (str.charAt(0) == 'я') {
                tmpStr.append("йа");
            } else if (str.charAt(0) == 'Я') {
                tmpStr.append("ЙА");
            } else if (str.charAt(0) == 'ю') {
                tmpStr.append("йу");
            } else if (str.charAt(0) == 'Ю') {
                tmpStr.append("ЙУ");
            } else if (str.charAt(0) == 'ё') {
                tmpStr.append("йо");
            } else if (str.charAt(0) == 'Ё') {
                tmpStr.append("ЙО");
            } else if (str.charAt(0) == 'й') {
                tmpStr.append("&й");
            } else if (str.charAt(0) == 'Й') {
                tmpStr.append("&Й");
            } else {
                tmpStr.append(str.charAt(0));
            }

            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i) == 'й') {
                    tmpStr.append("&й");
                } else if (str.charAt(i) == 'Й') {
                    tmpStr.append("&Й");
                } else if (((str.toLowerCase().charAt(i-1) == 'й')||(str.toLowerCase().charAt(i-1) == 'ь')) &&
                        ((str.toLowerCase().charAt(i) == 'а') || (str.toLowerCase().charAt(i) == 'у') || (str.toLowerCase().charAt(i) == 'о'))) {
                    tmpStr.append("$");
                    tmpStr.append(str.charAt(i));
                } else if (isGlas(str.charAt(i - 1)) && str.charAt(i) == 'ё' ) {
                    tmpStr.append("&йо");
                } else if (isGlas(str.charAt(i - 1)) && str.charAt(i) == 'ю' ) {
                    tmpStr.append("&йу");
                } else if (isGlas(str.charAt(i - 1)) && str.charAt(i) == 'я' ) {
                    tmpStr.append("&йа");
                } else if (isGlas(str.charAt(i - 1)) && str.charAt(i) == 'Ё' ) {
                    tmpStr.append("&ЙО");
                } else if (isGlas(str.charAt(i - 1)) && str.charAt(i) == 'Ю' ) {
                    tmpStr.append("&ЙУ");
                } else if (isGlas(str.charAt(i - 1)) && str.charAt(i) == 'Я' ) {
                    tmpStr.append("&ЙА");
                } else if (str.charAt(i) == 'я') {
                    tmpStr.append("йа");
                } else if (str.charAt(i) == 'Я') {
                    tmpStr.append("ЙА");
                } else if (str.charAt(i) == 'ю') {
                    tmpStr.append("йу");
                } else if (str.charAt(i) == 'Ю') {
                    tmpStr.append("ЙУ");
                } else if (str.charAt(i) == 'ё') {
                    tmpStr.append("йо");
                } else if (str.charAt(i) == 'Ё') {
                    tmpStr.append("ЙО");
                } else if (str.charAt(i) == 'ь'){
                    tmpStr.append("й");
                } else if (str.charAt(i) == 'Ь'){
                    tmpStr.append("Й");
                }
                else tmpStr.append(str.charAt(i));
            }
            System.out.println(tmpStr.toString());
            /*
            е с условиями
             */
        }
        return tmpStr.toString();
    }

    private boolean isGlas(char x){
        return x == 'а'|| x =='у' || x == 'е' || x =='ы'  || x == 'о'  || x == 'э'  || x == 'я'  || x =='и'  || x =='ю'  || x == 'ё'  || x == 'й'  || x == '́' ||
                x == 'А'|| x =='У' || x == 'Е' || x =='Ы'  || x == 'О'  || x == 'Э'  || x == 'Я'  || x =='И'  || x =='Ю'  || x == 'Ё'  || x == 'Й';
    }

    public String modZi(String str) {
        StringBuilder tmpStr = new StringBuilder();
        tmpStr.append(str.charAt(0));
        for (int i = 1; i < str.length(); i++) {
            if ((str.charAt(i - 1) == 'ц') || (str.charAt(i - 1) == 'Ц')) {
                if (str.charAt(i) == 'ы') {
                    tmpStr.append("и");
                } else {
                    if (str.charAt(i) == 'и') {
                        tmpStr.append("&и");
                    }
                }
            } else
                tmpStr.append(str.charAt(i));
        }
        return tmpStr.toString();
    }

    public String getFinStr(String str, short modU) {
        StringBuilder tmpStr = new StringBuilder();
        for (int i = 0; i < str.length()-1; i++) {
            if ((str.charAt(i) == '$') && (str.charAt(i+1) == '$')){
                tmpStr.append('$');
                i++;
                continue;
            }
            if ((str.charAt(i) == '$') && (str.charAt(i+1) == '&')){
                tmpStr.append('&');
                i++;
                continue;
            }
            if ((str.charAt(i) == '&') && (str.charAt(i + 1) == 'э')) {
                tmpStr.append("э");
                i++;
                continue;
            } else {
                if ((i != 0) && (str.charAt(i) == 'э') && (str.charAt(i - 1) != '&')) {
                    tmpStr.append("е");
                    continue;
                }
            }
            if ((str.charAt(i) == '&') && (str.charAt(i + 1) == 'Э')) {
                tmpStr.append("Э");
                i++;
                continue;
            } else {
                if ((i != 0) && (str.charAt(i) == 'Э') && (str.charAt(i - 1) != '&')) {
                    tmpStr.append("Е");
                    continue;
                }
            }
            if ((str.charAt(i) == '&') && (str.charAt(i + 1) == 'и')) {
                tmpStr.append("и");
                i++;
                continue;
            }
            if ((str.charAt(i) == 'ц') && (str.charAt(i + 1) == 'и')) {
                tmpStr.append("цы");
                i++;
                continue;
            }
            if ((str.charAt(i) == 'Ц') && (str.charAt(i + 1) == 'и')) {
                tmpStr.append("Цы");
                i++;
                continue;
            }
            if (modU != 0) {
                if ((str.charAt(i) == 'й') && (str.charAt(i + 1) == 'а')) {
                    tmpStr.append("я");
                    i++;
                    continue;
                }
                if ((str.charAt(i) == 'й') && (str.charAt(i + 1) == 'у')) {
                    tmpStr.append("ю");
                    i++;
                    continue;
                }
                if ((str.charAt(i) == 'й') && (str.charAt(i + 1) == 'о')) {
                    tmpStr.append("ё");
                    i++;
                    continue;
                }
                if ((str.charAt(i) == 'Й') && (str.charAt(i + 1) == 'А')) {
                    tmpStr.append("Я");
                    i++;
                    continue;
                }
                if ((str.charAt(i) == 'Й') && (str.charAt(i + 1) == 'У')) {
                    tmpStr.append("Ю");
                    i++;
                    continue;
                }
                if ((str.charAt(i) == 'Й') && (str.charAt(i + 1) == 'О')) {
                    tmpStr.append("Ё");
                    i++;
                    continue;
                }
                if (modU ==2){
                    if ((str.charAt(i) == '&') && (str.charAt(i + 1) == 'й')) {
                        tmpStr.append("й");
                        i++;
                        continue;
                    }
                    if ((str.charAt(i) == '&') && (str.charAt(i + 1) == 'Й')) {
                        tmpStr.append("Й");
                        i++;
                        continue;
                    }
                    if (str.charAt(i) == 'й') {
                        tmpStr.append("ь");
                        continue;
                    }
                    if (str.charAt(i) == 'Й') {
                        tmpStr.append("Ь");
                        continue;
                    }
                }
            }

            if ((str.charAt(i) == '&') || (str.charAt(i) == '$')) {
                continue;
            }
            tmpStr.append(str.charAt(i));

        }
        return tmpStr.toString();
    }
}




