$(function () {

    var ERROR_SUBMIT = "Total Amount and Tendered Amount are not tally.";

    $(".rb").on('change', function(){

        var bookingType = $(this).val();
        var name = $("#nname").val();


        if(name!=""){
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

            $('#showSummary').hide();
            $('#showPayment').hide();
            $('#showBtn').hide();

            if(bookingType=="book") {
                $('#showRemarks').hide();

            }else if(bookingType=="block"){
                $('#showRemarks').show();
            }
        }

    });

    $("#nric").change(function(){
        document.getElementById("checkNRIC").style.color='grey';
        $("#checkNRIC").html('<i class="glyphicon glyphicon-ok-circle"></i>');
    });

    $("#p1").on('change', function(){
        var p1 = $(this).val();
        var p2 = $("#p2").val();
        var p3 = $("#p3").val();
        var p4 = $("#p4").val();
        var p5 = $("#p5").val();
        var p6 = $("#p6").val();
        var p7 = $("#p7").val();
        var totalPayable = $("#totalPayable").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalPayable)-Number(tenderedAmt)).toFixed(2);

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
        var totalPayable = $("#totalPayable").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalPayable)-Number(tenderedAmt)).toFixed(2);

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
        var totalPayable = $("#totalPayable").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalPayable)-Number(tenderedAmt)).toFixed(2);

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
        var totalPayable = $("#totalPayable").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalPayable)-Number(tenderedAmt)).toFixed(2);

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
        var totalPayable = $("#totalPayable").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalPayable)-Number(tenderedAmt)).toFixed(2);

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
        var totalPayable = $("#totalPayable").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalPayable)-Number(tenderedAmt)).toFixed(2);

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
        var totalPayable = $("#totalPayable").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalPayable)-Number(tenderedAmt)).toFixed(2);

        $("#balance").text(balance);
        $("#tenderedAmt").text(tenderedAmt);

    });

    $("#bookingSubmit").click(function(e) {
        e.preventDefault();

        var totalPayable = $("#totalPayable").text();
        var tenderedAmt = $("#tenderedAmt").text();
        var balance = $("#balance").text();

        var mobile = $("#phone").val();

        mobile = mobile.replace("(65) ","").replace(/_/g,"").replace(" ","");
        var mobileLength = mobile.length;

        var radios = document.getElementsByName('bookingType');

        var bookingType;
        for (var i = 0, length = radios.length; i < length; i++) {
            if (radios[i].checked) {
                bookingType = radios[i].value;
                break;
            }
        }

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
            p2="0";
        }
        if(p3==""){
            p3="0";
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
        var BLUE_VOUCHER = "HomeTeamNS Blue Voucher";
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

        $("#bTotalPayable").val(totalPayable);
        $("#bDesc").val(desc);
        $("#bAmount").val(amount);
        $("#bTenderAmt").val(tenderedAmt);

        if(mobileLength==0){

            alert("Phone is required.");

        }else if(mobileLength<8){

            alert("Invalid Phone");

        }else {

            if (bookingType == "book") {

                if (balance != "0.00") {
                    alert(ERROR_SUBMIT);

                } else {
                    $("#addBooking").submit();

                }
            } else if (bookingType == "block") {

                var remark = $("#remarks").val();
                if(remark==""){
                    alert("Please fill in remarks for blocking chalet");
                }else{

                    $("#addBooking").submit();
                }

            } else {
                $("#addBooking").submit();
            }
        }
    });


    $("#deleteSubmit").click(function(e){
        e.preventDefault();

        var action = "delete";

        $("#dAction").val(action);

        var r = confirm("Do you want to remove this blocking?");

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