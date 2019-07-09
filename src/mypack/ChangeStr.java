package mypack;

public class ChangeStr {
    public String modE(String str, int modifyE) {
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

    public String modU(String str) {
        StringBuilder tmpStr = new StringBuilder();
        //tmpStr.append(str.charAt(0));
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
        return tmpStr.toString();
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

    public String getFinStr(String str, int modU) {
        StringBuilder tmpStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
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
            if (modU == 1) {
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
            }
            if ((str.charAt(i) == '&') || (str.charAt(i) == '$')) {
                continue;
            }
            tmpStr.append(str.charAt(i));

        }
        return tmpStr.toString();
    }
}




