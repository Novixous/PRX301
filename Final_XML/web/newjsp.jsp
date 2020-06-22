<%-- 
    Document   : index
    Created on : Jun 22, 2020, 2:23:35 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSDOM</title>
        <script type="text/javascript">
            var count = 0;
            var cells = [];
            var new_XMLDOM = null;
            var xmlHttp;
            function addRow(tableId, cells) {
                var tableElem = document.getElementById(tableId);
                var newRow = tableElem.insertRow(tableElem.rows.length);
                var newCell;
                for (var i = 0; i < cells.length; i++) {
                    newCell = newRow.insertCell(newRow.cells.length);
                    newCell.innerHTML = cells[i];
                }
                return newRow;
            }
            function deleteRow(tableId, rowNumber) {
                var tableElem = document.getElementById(tableId);
                if (rowNumber > 0 && rowNumber < tableElem.rows.length) {
                    tableElem.deleteRow(rowNumber);
                } else {
                    alert("Failed");
                }
            }
            function getXmlHttpObject() {
                var xmlHttp = null;
                try { // firefox, Opera 8.0 +, Safari 
                    xmlHttp = new XMLHttpRequest();
                } catch (e) { // IE
                    try {
                        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (e) {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                }
                return xmlHttp;
            }
            function searchNode(node, strSearch, tableName) {
                if (node == null) {
                    return;
                }
                if (node.tagName == "booktitle") {
                    var tmp = node.firstChild.nodeValue;
                    if (tmp.indexOf(strSearch, 0) > -1) {
                        var parent = node.parentNode;
                        var attrID = parent.attributes.getNamedItem("id").text;
                        new_XMLDOM += "<book id ='" + attrID + "'>";
                        count++;
                        cells[0] = count;
                        cells[1] = parent.attributes.getNamedItem("id").text;
                        cells[2] = node.firstChild.nodeValue;
                        new_XMLDOM += "<booktitle>" +
                                node.firstChild.nodeValue + "</booktitle>";
                        var author = node.nextSibling; // avoid white space
                        cells[3] = author.firstChild.nodeValue;
                        new_XMLDOM += "<author>" +
                                author.firstChild.nodeValue + "</author>";

                        var price = author.nextSibling; // avoid white space
                        cells[4] = price.firstChild.nodeValue;
                        new_XMLDOM += "<price>" +
                                author.firstChild.nodeValue + "</price>";
                        addRow(tableName, cells);
                        new_XMLDOM += "</book>";
                    }
                }
                var childs = node.childNodes;
                for (var i = 0; i < childs.length; i++) {
                    searchNode(childs[i], strSearch, tableName)
                }
            }
            function traversalDOMTree(fileName, tableName) {
                var tableElem = document.getElementById(tableName);
                var i = 1;
                while (i < tableElem.rows.length) {
                    deleteRow(tableName, i);
                }
                count = 0;
                var xmlDOM = new ActiveXObject("Microsoft.XMLDOM");
                new_XMLDOM = '<library xmlns="http://xml.netbeans.org/schema/library">';
                xmlDOM.async = false;
                xmlDOM.load(fileName);
                if (xmlDOM.parseError.errorCode != 0) {
                    alert("Error: " + xmlDOM.parseError.reason);
                } else {
                    searchNode(xmlDOM, myForm.txtSearch.value, tableName);
                    new_XMLDOM += "</library>";
                    alert(new_XMLDOM);//4 debug

                }
            }
            function update() {
                xmlHttp = getXmlHttpObject();
                if (xmlHttp == null) {
                    alert("YOUR BROWSER DOES NOT SUPPORT AJAXXXX");
                    return;
                }
                xmlHttp.open("POST", "Controller", true);
                xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencode");
                var url = "btnAction=update&xmlContent=";
                url += new_XMLDOM;
                xmlHttp.send(url);
            }
        </script>
    </head>
    <body>
        <h1>JavaScript with DOM Demo!</h1>
        <form name="myForm">
            Name: <input type="text" name="txtSearch" value=""/> <br/>
            <input type="button" value="Search" onclick="traversalDOMTree('./library.xml', 'dataTable')"

        </form>
        <table border="1" id="dataTable">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Code</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Price</th>
                </tr>
            </thead>
        </table>
    </body>
</html>