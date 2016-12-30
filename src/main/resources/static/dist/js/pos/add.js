$(function () {

    var ERROR_SUBMIT = "Total Amount and Tendered Amount are not tally.";

    $(".rb").on('change', function(){

        var bookingType = $(this).val();
        var nric = $("#nric").val();


        if(nric!=""){
            if(bookingType=="book") {

                $('#showSummary').show();
                $('#showPayment').show();
                $('#showRemarks').hide();
                $('#showBtn').show();
                $('#submitBtnText').text("Make Payment");

            }else if(bookingType=="block"){

                $('#showRemarks').show();
                $('#showSummary').show();
                $('#showPayment').hide();
                $('#showBtn').show();
                $('#submitBtnText').text("Block Now");

            }
        }else{
            $('#showRemarks').hide();
            $('#showSummary').hide();
            $('#showPayment').hide();
            $('#showBtn').hide();
        }

    });

    function checkNRIC(nric, chaletID, startDate, endDate) {

        var parameter = {
            'nric': nric,
            'chaletId':chaletID,
            'startDate':startDate,
            'endDate':endDate
        };

        $.ajax({
            type: 'POST',
            url: '/ajax/member/chalet-booking/',
            data: parameter,
            dataType: 'json',
            success: function (data) {

               // console.log("data:", data);

                var dataContact = data.contact;
                var dataTable = data.dates; //data to display in summary table

                if(dataContact!=null){

                    document.getElementById("checkNRIC").style.color='green';
                    $("#checkNRIC").html('<i class="glyphicon glyphicon-ok-circle"></i>');

                    $('#nname').val(dataContact.contactExtension.fullName);
                    $('#email').val(dataContact.email);
                    $('#phone').val(dataContact.mobilePhone);

                    document.getElementById('nname').readOnly = true;
                    //document.getElementById('email').readOnly = true;
                    //document.getElementById('phone').readOnly = true;

                }else{

                    document.getElementById("checkNRIC").style.color='red';
                    $("#checkNRIC").html('<i class="glyphicon glyphicon-remove"></i>');
                }

                if(dataTable.length>0){

                    $("#summaryBody").remove();

                    var tbody = document.createElement('tbody'), tr, td, row, cell;
                    tbody.id="summaryBody";

                    var totalSum = 0;
                    var count = 0;
                    var listBookingSummary="";
                    var COMA = ",";
                    var DELIMITER = "|";

                    //display data in summary table
                    for(var details in dataTable){

                        var bDate = dataTable[details].date;
                        var bTypeName = dataTable[details].dayName;
                        var bType = dataTable[details].dayType;
                        var bCost = dataTable[details].dayCost;
                        var bGst = dataTable[details].dayGst;
                        var bTotal = dataTable[details].dayTotal;

                        //change date format
                        var bDateSPlit = bDate.split("-");  //yyyy-mm-dd
                        bDate = bDateSPlit[2] + "-" + bDateSPlit[1] + "-" + bDateSPlit[0];  //dd-mm-yyyy

                        if(listBookingSummary==""){
                            listBookingSummary += bDate + COMA + bType + COMA + bTypeName + COMA + bCost + COMA + bGst + COMA + bTotal;

                        }else{
                            listBookingSummary += DELIMITER + bDate + COMA + bType + COMA + bTypeName + COMA + bCost + COMA + bGst + COMA + bTotal;

                        }

                        $("#listBookingSummary").val(listBookingSummary);


                         totalSum += Number(bTotal);

                        tr = document.createElement('tr');

                        td1 = document.createElement('td');
                        td1.setAttribute('name', "bookingDate["+count+"]");
                        td1.style.textAlign="left";
                        tr.appendChild(td1);
                        td1.innerHTML = bDate;

                        td2 = document.createElement('td');
                        td2.setAttribute('name', "dayName["+count+"]");
                        td2.style.textAlign="left";
                        tr.appendChild(td2);
                        td2.innerHTML = bTypeName;

                        td3 = document.createElement('td');
                        td3.setAttribute('name', "cost["+count+"]");
                        td3.style.textAlign="right";
                        tr.appendChild(td3);
                        td3.innerHTML = bCost;

                        td4 = document.createElement('td');
                        td4.setAttribute('name', "gst["+count+"]");
                        td4.style.textAlign="right";
                        tr.appendChild(td4);
                        td4.innerHTML = bGst;

                        td5 = document.createElement('td');
                        td5.setAttribute('name', "totalCost["+count+"]");
                        td5.style.textAlign="right";
                        tr.appendChild(td5);
                        td5.innerHTML = bTotal;

                        tbody.appendChild(tr);

                        count++;
                    }
                    document.getElementById('summaryTable').appendChild(tbody);


                    totalSum = totalSum.toFixed(2);
                    $("#totalPayable").text(totalSum);
                    $("#balance").text(totalSum);
                    $("#tenderedAmt").text("0.00");

                    var bookingType="";

                    if (document.getElementById('b1').checked) {
                        bookingType = document.getElementById('b1').value;

                    }else if (document.getElementById('b2').checked) {
                        bookingType = document.getElementById('b2').value;
                    }

                    if(bookingType=="book"){

                        $('#showSummary').show();
                        $('#showPayment').show();
                        $('#showRemarks').hide();
                        $('#showBtn').show();
                        $('#submitBtnText').text("Make Payment");

                    }else if(bookingType=="block"){

                        $('#showRemarks').show();
                        $('#showSummary').show();
                        $('#showPayment').hide();
                        $('#showBtn').show();
                        $('#submitBtnText').text("Block Now");

                    }else{
                        $('#showSummary').show();
                    }

                    $('#showUnavailable').hide();
                    document.getElementById("b1").disabled = false;
                    document.getElementById("b2").disabled = false;

                }else{

                    $('#showUnavailable').show();
                    document.getElementById("b1").disabled = true;
                    document.getElementById("b2").disabled = true;

                    $('#showSummary').hide();
                    $('#showPayment').hide();
                    $('#showRemarks').hide();
                    $('#showBtn').hide();

                }
            }
        });
    }

    $("#p1").on('change', function(){
        var p1 = $(this).val();
        var p2 = $("#p2").val();
        var p3 = $("#p3").val();
        var p4 = $("#p4").val();
        var p5 = $("#p5").val();
        var p6 = $("#p6").val();
        var p7 = $("#p7").val();
        var totalAmt = $("#totalAmt").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalAmt)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#p2").on('change', function(){
        var p1 = $("#p1").val();
        var p2 = $(this).val();
        var p3 = $("#p3").val();
        var p4 = $("#p4").val();
        var p5 = $("#p5").val();
        var p6 = $("#p6").val();
        var p7 = $("#p7").val();
        var totalAmt = $("#totalAmt").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalAmt)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#p3").on('change', function(){
        var p1 = $("#p1").val();
        var p2 = $("#p2").val();
        var p3 = $(this).val();
        var p4 = $("#p4").val();
        var p5 = $("#p5").val();
        var p6 = $("#p6").val();
        var p7 = $("#p7").val();
        var totalAmt = $("#totalAmt").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalAmt)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#p4").on('change', function(){
        var p1 = $("#p1").val();
        var p2 = $("#p2").val();
        var p3 = $("#p3").val();
        var p4 = $(this).val();
        var p5 = $("#p5").val();
        var p6 = $("#p6").val();
        var p7 = $("#p7").val();
        var totalAmt = $("#totalAmt").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalAmt)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#p5").on('change', function(){
        var p1 = $("#p1").val();
        var p2 = $("#p2").val();
        var p3 = $("#p3").val();
        var p4 = $("#p4").val();
        var p5 = $(this).val();
        var p6 = $("#p6").val();
        var p7 = $("#p7").val();
        var totalAmt = $("#totalAmt").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalAmt)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#p6").on('change', function(){
        var p1 = $("#p1").val();
        var p2 = $("#p2").val()
        var p3 = $("#p3").val();
        var p4 = $("#p4").val();
        var p5 = $("#p5").val();
        var p6 = $(this).val();
        var p7 = $("#p7").val();
        var totalAmt = $("#totalAmt").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalAmt)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#p7").on('change', function(){
        var p1 = $("#p1").val();
        var p2 = $("#p2").val()
        var p3 = $("#p3").val();
        var p4 = $("#p4").val();
        var p5 = $("#p5").val();
        var p6 = $("#p6").val();
        var p7 = $(this).val();
        var totalAmt = $("#totalAmt").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalAmt)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#btnSubmit").click(function(e) {
        e.preventDefault();

        var pItemObjList = [];
        var itemObj = {};
        var totalAmt = $("#totalAmt").text();
        var tenderedAmt = $("#tenderedAmt").text();
        var balance = $("#balance").text();

        // get all the cells in each row.
        var rowCount = document.getElementById("addedListBody").rows.length;
        console.log("btnSubmit - AddedListBody rowCount: "+rowCount);

        var listProducts="";
        var COMA = "~";
        var DELIMITER = "|";

        for(i=0; i<rowCount; i++){
            itemObj = new Object();

            var rowObj = document.getElementById("addedListBody").rows[i];
            var trID = rowObj.id;
            var trIDArray = trID.split("_");
            itemObj['itemID'] = trIDArray[1];

            itemObj['nric'] = rowObj.cells[0].innerHTML;
            itemObj['memberName'] = rowObj.cells[1].innerHTML;
            itemObj['itemDesc'] = rowObj.cells[2].innerHTML;
            var nonGSTPrice = rowObj.cells[3].innerHTML;
            itemObj['itemPrice'] = (nonGSTPrice / 0.93).toFixed(2);     // get the price with GST.
            itemObj['reqQty'] = rowObj.cells[4].innerHTML;
            itemObj['total'] = rowObj.cells[7].innerHTML;
            itemObj['email'] = rowObj.cells[8].innerHTML;
            itemObj['mobileNum'] = rowObj.cells[9].innerHTML;

            if(listProducts==""){
                listProducts += itemObj.itemID + COMA + itemObj.nric + COMA + itemObj.memberName + COMA + itemObj.itemDesc + COMA + itemObj.reqQty + COMA + itemObj.itemPrice + COMA + itemObj.total  + COMA + itemObj.email  + COMA + itemObj.mobileNum;
                console.log("i= "+i+" | listProducts: "+listProducts);

            }else{
                listProducts += DELIMITER + itemObj.itemID + COMA + itemObj.nric + COMA + itemObj.memberName + COMA + itemObj.itemDesc + COMA + itemObj.reqQty + COMA + itemObj.itemPrice + COMA + itemObj.total + COMA + itemObj.email  + COMA + itemObj.mobileNum;
            }
        }
        console.log("listProducts: "+listProducts);

        // set the list of selected Products and NRIC.
        $("#listBookingSummary").val(listProducts);


        var d1 = $("#d1").val();
        var d2 = $("#d2").val();
        var d3 = $("#d3").val();
        var d4 = $("#d4").val();
        var d5 = $("#d5").val();
        var d6 = $("#d6").val();
        var d7 = $("#d7").val();

        var p1 = $("#p1").val();
        var p2 = $("#p2").val();
        var p3 = $("#p3").val();
        var p4 = $("#p4").val();
        var p5 = $("#p5").val();
        var p6 = $("#p6").val();
        var p7 = $("#p7").val();

        if(p1==""){
            p1="0";
        }
        if(p2==""){
            p3="0";
        }
        if(p3==""){
            p4="0";
        }
        if(p4==""){
            p4="0";
        }
        if(p5==""){
            p5="0";
        }
        if(p6==""){
            p6="0";
        }
        if(p7==""){
            p7="0";
        }
        var desc="";
        var amount="";
        var SEPARATOR = "|";
        var CASH = "CASH";
        var CHEQUE = "Cheque/Cashier Order/Money Order";
        var VOUCHERS = "Vouchers"
        var CREDIT_CARD = "Credit Card";
        var DINERS = "Diners";
        //var BLUE_VOUCHER = "HomeTeamNS Blue Voucher";
        var BLUE_VOUCHER = "HomeTeamNS Voucher";
        var NETS = "Nets";

        if(d1==""){
            desc += CASH + SEPARATOR;
        }else{
            desc += d1 + SEPARATOR;
        }

        if(d2==""){
            desc += CHEQUE + SEPARATOR;
        }else{
            desc += d2 + SEPARATOR;
        }

        if(d3==""){
            desc += VOUCHERS + SEPARATOR;
        }else{
            desc += d3 + SEPARATOR;
        }

        if(d4==""){
            desc += CREDIT_CARD + SEPARATOR;
        }else{
            desc += d4 + SEPARATOR;
        }

        if(d5==""){
            desc += DINERS + SEPARATOR;
        }else{
            desc += d5 + SEPARATOR;
        }

        if(d6==""){
            desc += BLUE_VOUCHER + SEPARATOR;
        }else{
            desc += d6 + SEPARATOR;
        }

        if(d7==""){
            desc += NETS;
        }else{
            desc += d7;
        }

        amount += p1 + SEPARATOR;
        amount += p2 + SEPARATOR;
        amount += p3 + SEPARATOR;
        amount += p4 + SEPARATOR;
        amount += p5 + SEPARATOR;
        amount += p6 + SEPARATOR;
        amount += p7;

        $("#bTotalPayable").val(totalAmt);
        $("#bDesc").val(desc);          //  desc of the payment.
        $("#bAmount").val(amount);      // amount based on the payment type.
        $("#bTenderAmt").val(tenderedAmt);      // total tendered amount

            if (balance != "0.00") {
                alert(ERROR_SUBMIT);
            } else {
                //$("#addBooking").submit();
                $("#pos-add-form").submit();
            }
    });


    $("#deleteSubmit").click(function(e){
        e.preventDefault();

        var action = "delete";
        $("#dAction").val(action);
        var r = confirm("Do you want to delete this booking?");

        if(r==true){
            this.form.submit();
        }

    });

    $("#cancelSubmit").click(function(e){
        e.preventDefault();

        var action = "cancel";
        $("#dAction").val(action);
        var r = confirm("Do you want to cancel this booking?");

        if(r==true){
            this.form.submit();
        }
    });

});