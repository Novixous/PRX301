/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Toan
 */
public class WellFormer {

    InputStreamReader isr = null;
    BufferedReader br = null;
    PrintWriter pw = null;

    ArrayList<String> htmlContent = null;
    String htmlLine;
    String tagChain;

    private boolean init(InputStream is) {
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);

        try {
            //Loại bỏ tất cả phần head, chỉ lấy body
            while (true) {
                htmlLine = br.readLine();
                if (htmlLine.contains("</head>")) {
                    htmlContent = new ArrayList<>();
                    tagChain = "";

                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void createWellFormedXML(InputStream is, String fileName) {
        if (!init(is)) {
            return;
        }

        try {
            int line = -1;
            while ((htmlLine = br.readLine()) != null) {
                line++;
                htmlLine = htmlLine.trim();
                checkEntity();

                char c;
                for (int i = 0; i < htmlLine.length(); i++) {
                    c = htmlLine.charAt(i);

                    if (c == '<') {
                        if (htmlLine.startsWith("<!--", i)) {
                            i = checkComment(i);
                        } else if (htmlLine.startsWith("<script", i)) {
                            i = checkScript(i);
                        } else {
                            i++;
                            c = htmlLine.charAt(i);
                            if (c != ' ') {
                                int lesserThanPosition = i - 1;
                                String tagName = "";

                                if (c != '/') {
                                    tagName += c;
                                }
                                for (i++; i < htmlLine.length(); i++) {
                                    c = htmlLine.charAt(i);
                                    if (c != ' ' && c != '>' && c != '/') {
                                        tagName += c;
                                    } else {
                                        break;
                                    }
                                }

                                i = checkTag(i, lesserThanPosition, tagName, line);
                            }
                        }
                    }
                }

                htmlContent.add(htmlLine);
            }

            saveToXMLFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void checkEntity() {
        char c;
        for (int i = 0; i < htmlLine.length(); i++) {
            c = htmlLine.charAt(i);

            if (c == '&') {
                String s = htmlLine.substring(i + 1);
                //Nếu không gặp kí hiệu entity nào xuất hiện thì đổi
                //"&" thành "&amp;"
                if (!(s.startsWith("nbsp;") || s.startsWith("lt;")
                        || s.startsWith("gt;") || s.startsWith("amp;")
                        || s.startsWith("quot;") || s.startsWith("apos;"))) {

                    htmlLine = htmlLine.substring(0, i + 1);
                    htmlLine += "amp;";
                    htmlLine += s;

                    i += 4;
                }
            }
        }
    }

    private int checkComment(int startPosition) {
        int index = htmlLine.indexOf("-->", startPosition + 4);
        if (index >= 0) {
            return index + 2;
        }

        while (true) {
            try {
                htmlLine += " " + br.readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }

            index = htmlLine.indexOf("-->");
            if (index >= 0) {
                return index + 2;
            }
        }
    }

    private int checkScript(int startPosition) {
        if (htmlLine.startsWith("<script>", startPosition)) {
            int start = startPosition + 8, end;

            while (true) {
                end = htmlLine.indexOf("</script>", start);
                if (end < 0) {
                    end = htmlLine.length();
                }

                char c;
                for (int i = start; i < end; i++) {
                    c = htmlLine.charAt(i);

                    if (c == '<') {
                        String s = htmlLine.substring(i + 1);
                        htmlLine = htmlLine.substring(0, i) + "&lt;" + s;
                        end += 3;
                    } else if (c == '>') {
                        String s = htmlLine.substring(i + 1);
                        htmlLine = htmlLine.substring(0, i) + "&gt;" + s;
                        end += 3;
                    }
                }

                if (end == htmlLine.length()) {
                    try {
                        start = end;
                        htmlLine += br.readLine().trim();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    return end + 8;
                }
            }
        }

        int index = htmlLine.indexOf("/>", startPosition + 6);
        if (index >= 0) {
            return index + 1;
        } else {
            index = htmlLine.indexOf("</script>", startPosition + 6);

            if (index >= 0) {
                return index + 8;
            }
        }

        while (true) {
            try {
                htmlLine += br.readLine().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            index = htmlLine.indexOf("/>", startPosition + 6);
            if (index >= 0) {
                return index + 1;
            } else {
                index = htmlLine.indexOf("</script>", startPosition + 6);

                if (index >= 0) {
                    return index + 8;
                }
            }
        }
    }

    private int checkTag(int startPosition, int lesserThanPosition, String tagName, int line) {
        if (line == 370) {
            String s = "";
        }
        //Nối các dòng nếu từ vị trí hiện tại của dòng hiện tại chưa xuất hiện có kí tự ">"
        int index = htmlLine.indexOf(">", startPosition);
        while (index < 0) {
            try {
                htmlLine += " " + br.readLine().trim();
                index = htmlLine.indexOf(">", startPosition);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Các biến cho việc kiểm tra Attribute
        int find = 0, attribute = 0, space = 1, equal = 2, firstDoubleQout = 3, secondDoubleQout = 4;
        int start = 0;

        char c;
        for (int i = startPosition; i < htmlLine.length(); i++) {
            c = htmlLine.charAt(i);
            if (c == '>') {
                c = htmlLine.charAt(i - 1);

                //Nếu attribute chưa hoàn chỉnh thì phải xóa trước khi xuất hiện kí hiệu ">"
                // <div class="..." id> thành <div class="...">
                if (find != attribute && c != '/') {
                    String s = htmlLine.substring(i);
                    htmlLine = htmlLine.substring(0, start) + s;

                    find = attribute;
                    i = start;
                }
                ////////

                //Bảng mã ascii thì A = 65, Z = 90, a = 97 và z = 122 
                if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c >= 48 && c <= 57)) {
                    if (htmlLine.charAt(lesserThanPosition + 1) == '/') {
                        //Đang ở vị trí tag đóng "</...>"
                        int lastIndex = tagChain.lastIndexOf(tagName + "-");
                        if (lastIndex >= 0) {
                            String subTagChain = tagChain.substring(lastIndex);
                            String[] tags = subTagChain.split(";"), tagInfo;
                            String tag, aHtmlLine;

                            //Các tag con chưa đóng sẽ được thêm dấu "/" vào
                            // "<br>" => "<br/>"
                            for (int j = tags.length - 1; j > 0; j--) {
                                tag = tags[j];
                                tagInfo = tag.split("-");

                                if (line != Integer.parseInt(tagInfo[1])) {
                                    aHtmlLine = htmlContent.get(Integer.parseInt(tagInfo[1]));

                                    String s = aHtmlLine.substring(Integer.parseInt(tagInfo[2]));
                                    aHtmlLine = aHtmlLine.substring(0, Integer.parseInt(tagInfo[2]));
                                    aHtmlLine += "/" + s;
                                    htmlContent.set(Integer.parseInt(tagInfo[1]), aHtmlLine);
                                } else {
                                    String s = htmlLine.substring(Integer.parseInt(tagInfo[2]));
                                    htmlLine = htmlLine.substring(0, Integer.parseInt(tagInfo[2]));
                                    htmlLine += "/" + s;
                                }
                            }

                            tagChain = tagChain.substring(0, lastIndex - 1);
                            return i;
                        } else {
                            //Tag đóng nhưng chưa mở tag
                            // <div> </li> </div> thành <div> </div>
                            String s = htmlLine.substring(i + 1);
                            htmlLine = htmlLine.substring(0, lesserThanPosition);
                            htmlLine += s;
                            return lesserThanPosition - 1;
                        }
                    } else {
                        //Lưu lại tên tag, dòng và vị trí của dấu ">" khi mở tag
                        tagChain += ";" + tagName + "-" + line + "-" + i;
                        return i;
                    }
                } else if (c != '/') {
                    //Lưu lại tên tag, dòng và vị trí của dấu ">" khi mở tag
                    tagChain += ";" + tagName + "-" + line + "-" + i;
                    return i;
                }
            } else if (c == '<') {
                //Nếu vẫn đang kiểm tra attribute thì dấu "<" không phải là mở tag
                if (find == attribute) {
                    c = htmlLine.charAt(i + 1);

                    if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                        //tag chưa đóng bằng kí tự ">" mà tag khác đã được mở
                        String s = htmlLine.substring(i);
                        htmlLine = htmlLine.substring(0, i) + ">" + s;

                        tagChain += ";" + tagName + "-" + line + "-" + i;
                        return i;
                    }
                } else {
                    String s = htmlLine.substring(i + 1);
                    htmlLine = htmlLine.substring(0, i);
                    htmlLine += "&lt;" + s;
                }
            } else {
                //Kiểm tra artribute
                if (find == attribute) {
                    if (c != ' ') {
                        start = i;
                        find = space;
                    }
                } else if (find == space) {
                    if (c == ' ') {
                        find = equal;
                    } else if (c == '=') {
                        find = firstDoubleQout;
                    }
                } else if (find == equal) {
                    if (c == '=') {
                        find = firstDoubleQout;
                    } else if (c != ' ') {
                        //Xóa attribute nếu chưa hoàn chỉnh
                        //<div id class="..."> thành <div class="..."> 
                        String s = htmlLine.substring(i);
                        htmlLine = htmlLine.substring(0, start) + s;

                        find = attribute;
                        i = start - 1;
                    }
                } else if (find == firstDoubleQout) {
                    if (c == '"') {
                        find = secondDoubleQout;
                    } else if (c != ' ') {
                        //Xóa attribute nếu chưa hoàn chỉnh
                        //<div id= class="..."> thành <div class="..."> 
                        String s = htmlLine.substring(i);
                        htmlLine = htmlLine.substring(0, start) + s;

                        find = attribute;
                        i = start - 1;
                    }
                } else if (find == secondDoubleQout) {
                    if (c == '"') {
                        find = attribute;
                    }
                }
            }
        }

        return htmlLine.length();
    }

    private void saveToXMLFile(String fileName) {
        try {
            pw = new PrintWriter(fileName);
            pw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            for (int i = 0; i < htmlContent.size(); i++) {
                pw.write(htmlContent.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            if (pw != null) {
                pw.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
