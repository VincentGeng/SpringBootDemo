$(function () {

    var ERROR_SUBMIT = "There is Amount Balance left.";
    //default display

    $('#showAmendment').hide();
    $('#showRPayment').hide();
    $('#showPaymentBtn').hide();

    //default table data (No records found)
    var tbody = document.createElement('tbody'), tr, td, row, cell;
    tbody.id="summaryRBody";
    tr = document.createElement('tr');
    td1 = document.createElement('td');
    td1.setAttribute('colSpan','5');
    tr.appendChild(td1);
    td1.innerHTML = "No record(s) found!";
    tbody.appendChild(tr);
    document.getElementById('summaryRTable').appendChild(tbody);


    $("#rescheduleSubmit").click(function(e){
        e.preventDefault();

        var r = confirm("Do you want to reschedule this booking?");

        if(r==true){

            $(".deleteBooking").on('hidden.bs.modal', function(){
                $('.rescheduleBooking').modal('show');
            });

            $('.deleteBooking').modal('hide');

            var locID = $("#rLocationId").val();
            getRChaletType(locID);

        }
    });

    // Functions
    function getRChaletType(location) {

        var parameter = {
            'businessUnit' : location
        };

        $.ajax({
            type : 'POST',
            url: '/ajax/chalet-type/',		//1 AP - 1 group
            data : parameter,
            dataType : 'json',
            success : function(data) {

                $('#rChaletTypeId').empty();
                $('#rChaletTypeId').append($('<option>', {
                    value: "",
                    text : "SELECT"
                }));

                if((data==null)||(data=="")){
                    $('#rChaletTypeId').append($('<option>', {
                        value: "",
                        text : "No Chalet for this location"
                    }));
                }else{
                    $.each(data, function (i, data) {

                        $('#rChaletTypeId').append($('<option>', {
                            value: data.chaletTypeExtension.id,
                            text : data.chaletTypeExtension.type
                        }));
                    });
                }
            }
        });
    }


    //when chalet type is onchange
    $("#rChaletTypeId").on('change', function(){

        var chaletTypeId = $(this).val();
        var locationId =  $("#rLocationId").val();

        $('#rChaletId').empty();
        $('#rChaletId').append($('<option>', {
            value: "",
            text : "SELECT"
        }));

        getChaletName(chaletTypeId, locationId);

    });

    function getChaletName(chaletTypeId, locationId) {

        var parameter = {
            'chaletTypeId' : chaletTypeId,
            'businessUnitId' : locationId
        };

        $.ajax({
            type: 'POST',
            url: '/ajax/chalet/',		//1 AP - 1 group
            data: parameter,
            dataType: 'json',
            success: function (data) {

               // console.log("getChaletName:",data);

                if((data==null)||(data=="")){
                    $('#rChaletId').append($('<option>', {
                        value: "",
                        text : "No Chalet available"
                    }));
                }else{
                    $.each(data, function (i, data) {

                        $('#rChaletId').append($('<option>', {
                            value: data.chaletExtension.id,
                            text : data.chaletExtension.name
                        }));
                    });
                }
            }
        });
    }

    $('#checkAvailabilityBtn').on('click', function(){

        var chaletTypeId = $('#rChaletTypeId').val();
        var chaletId = $('#rChaletId').val();
        var bookingId = $('#rChaletBookingId').val();
        var invoiceId = $('#rInvoiceId').val();
        var checkinDate = $('#rCheckInDate').val();
        var checkoutDate = $('#rCheckOutDate').val();
        var checkoutDateDisplay = $('#rCheckOutDate').val();

        var newCheckOutDate = new Date();
        var parts = checkoutDate.split("-");
        newCheckOutDate.setFullYear(parts[2], parts[1]-1, parts[0]);
        newCheckOutDate.setDate(newCheckOutDate.getDate()-1);
        var dYear = newCheckOutDate.getFullYear();
        var dMonth = newCheckOutDate.getMonth()+1;
        var dDay = newCheckOutDate.getDate();
        checkoutDate = dDay + "-" + dMonth + "-" + dYear;

        var inSplit = checkinDate.split("-");
        var outSplit = checkoutDateDisplay.split("-");

        var nDate = new Date();
        var oDate = new Date();

        nDate.setFullYear(inSplit[2], inSplit[1]-1, inSplit[0]);
        oDate.setFullYear(outSplit[2], outSplit[1]-1, outSplit[0]);

        var timeDiff = Math.abs(oDate.getTime() - nDate.getTime());
        var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

        if(chaletTypeId==""){
            alert("Select Chalet Type");

        }else if(chaletId==""){
            alert("Select Chalet");

        }else if(checkinDate==""){
            alert("Select Check In Date");

        }else if(checkoutDate==""){
            alert("Select Check Out Date");

        }else if (diffDays >3) {

            alert("Only 3 days is allowed.");

        }else {
            checkBookingAvailability(bookingId, chaletId, checkinDate, checkoutDate);

        }

    });

    function checkBookingAvailability(bookingId, chaletId, checkinDate, checkoutDate) {

       // console.log("checkBookingAvailability:"+bookingId+"|"+chaletId+"|"+checkinDate+"|"+checkoutDate);
        var parameter = {
            'bookingId':bookingId,
            'chaletId':chaletId,
            'startDate':checkinDate,
            'endDate':checkoutDate
        };

        $.ajax({
            type: 'POST',
            url: '/ajax/chalet-availability/',
            data: parameter,
            dataType: 'json',
            success: function (data) {

                //console.log("checkBookingAvailability:",data);

                if((data==null)||(data=="")){

                }else{

                    var amendmentText = "Amendment Fees";
                    //var bookingDiffText = "Booking Fees Difference";
                    var invoiceNo = data.oldInvoiceNumber;
                    var amendmentFee = data.amendmentFee;
                    var invoiceTotal = data.oldInvoiceTotal;
                    var bookingDetailList = data.bookingDateList;

                    var listRBookingSummary = "";
                    var COMA = ",";
                    var DELIMITER = "|";
                    var totalSum = 0;

                    $("#rAmendment").val(amendmentFee);

                    if(bookingDetailList.length>0){

                        document.getElementById("rChaletTypeId").disabled=true;
                        document.getElementById("rChaletId").disabled=true;
                        document.getElementById("rCheckInDate").disabled=true;
                        document.getElementById("rCheckOutDate").disabled=true;

                        $('#checkAvailabilityBtn').hide();
                        $('#showAmendment').show();

                        //display data booking summary

                        //remove previous body
                        $("#summaryRBody").remove();

                        //create new body
                        var tbody = document.createElement('tbody'), tr, td, row, cell;
                        tbody.id="summaryRBody";

                        for(var details in bookingDetailList){

                            var bDate = bookingDetailList[details].date;
                            var bTypeName = bookingDetailList[details].dayName;
                            var bType = bookingDetailList[details].dayType;
                            var bCost = bookingDetailList[details].dayCost;
                            var bGst = bookingDetailList[details].dayGst;
                            var bTotal = bookingDetailList[details].dayTotal;

                            if(listRBookingSummary==""){
                                listRBookingSummary += bDate + COMA + bType + COMA + bTypeName + COMA + bCost + COMA + bGst + COMA + bTotal;

                            }else{
                                listRBookingSummary += DELIMITER + bDate + COMA + bType + COMA + bTypeName + COMA + bCost + COMA + bGst + COMA + bTotal;

                            }

                            $("#listRBookingSummary").val(listRBookingSummary);

                            totalSum += Number(bTotal);

                            tr = document.createElement('tr');

                            td1 = document.createElement('td');
                            td1.style.textAlign="left";
                            tr.appendChild(td1);
                            td1.innerHTML = bDate;

                            td2 = document.createElement('td');
                            td2.style.textAlign="left";
                            tr.appendChild(td2);
                            td2.innerHTML = bTypeName;

                            td3 = document.createElement('td');
                            td3.style.textAlign="right";
                            tr.appendChild(td3);
                            td3.innerHTML = bCost;

                            td4 = document.createElement('td');
                            td4.style.textAlign="right";
                            tr.appendChild(td4);
                            td4.innerHTML = bGst;

                            td5 = document.createElement('td');
                            td5.style.textAlign="right";
                            tr.appendChild(td5);
                            td5.innerHTML = bTotal;

                            tbody.appendChild(tr);
                        }

                        document.getElementById('summaryRTable').appendChild(tbody);
                        totalSum = totalSum.toFixed(2);
                        $("#rtotalPayable").text(totalSum);

                        var feeDifference = Number(totalSum) - Number(invoiceTotal) + Number(amendmentFee);
                        feeDifference = feeDifference.toFixed(2);

                        var rep = "";
                        //console.log("feeDifference:"+feeDifference);

                        if(feeDifference<0){
                            rep = feeDifference.replace("-","");
                            $("#rAmendmentChargeTxt").text("Total Refund (SGD):");
                            $("#rtotalCharge").text(rep);
                            $('#showPaymentBtn').show();
                            $('#paymentTxt').text("Confirm");
                        }else{
                            $("#rAmendmentChargeTxt").text("Total Charge (SGD):");
                            $("#rtotalCharge").text(feeDifference);
                            $("#rbalance").text(feeDifference);
                            $("#rtenderedAmt").text("0.00");
                            $('#showRPayment').show();
                            $('#showPaymentBtn').show();
                            $('#paymentTxt').text("Make Payment");
                        }


                        //remove previous body
                        $("#summaryABody").remove();

                        //create new body
                        var tbody = document.createElement('tbody'), tr, td, row, cell;
                        tbody.id="summaryABody";

                        //Invoice
                        tr = document.createElement('tr');
                        td1 = document.createElement('td');
                        td1.style.textAlign="left";
                        tr.appendChild(td1);
                        td1.innerHTML = "Invoice: "+invoiceNo;

                        td2 = document.createElement('td');
                        td2.style.textAlign="right";
                        tr.appendChild(td2);
                        td2.innerHTML = "("+invoiceTotal+")";
                        tbody.appendChild(tr);

                        //AmendmentFee
                        tr = document.createElement('tr');
                        td1 = document.createElement('td');
                        td1.style.textAlign="left";
                        tr.appendChild(td1);
                        td1.innerHTML = amendmentText;

                        td2 = document.createElement('td');
                        td2.style.textAlign="right";
                        tr.appendChild(td2);
                        td2.innerHTML = amendmentFee;
                        tbody.appendChild(tr);

                        // //Booking Fees Difference
                        // tr = document.createElement('tr');
                        // td1 = document.createElement('td');
                        // td1.style.textAlign="left";
                        // tr.appendChild(td1);
                        // td1.innerHTML = bookingDiffText;
                        //
                        // td2 = document.createElement('td');
                        // tr.appendChild(td2);
                        // td2.style.textAlign="right";
                        // td2.innerHTML = feeDifference;
                        // tbody.appendChild(tr);

                        document.getElementById('summaryATable').appendChild(tbody);

                    }

                }

            }
        });
    }

    $("#rp1").on('change', function(){
        var p1 = $(this).val();
        var p2 = $("#rp2").val();
        var p3 = $("#rp3").val();
        var p4 = $("#rp4").val();
        var p5 = $("#rp5").val();
        var p6 = $("#rp6").val();
        var p7 = $("#rp7").val();
        var totalCharge = $("#rtotalCharge").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalCharge)-Number(tenderedAmt)).toFixed(2);

        $("#rbalance").text(balance);
        $("#rtenderedAmt").text(tenderedAmt);

    });

    $("#rp2").on('change', function(){
        var p1 = $("#rp1").val();
        var p2 = $(this).val();
        var p3 = $("#rp3").val();
        var p4 = $("#rp4").val();
        var p5 = $("#rp5").val();
        var p6 = $("#rp6").val();
        var p7 = $("#rp7").val();
        var totalCharge = $("#rtotalCharge").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalCharge)-Number(tenderedAmt)).toFixed(2);

        $("#rbalance").text(balance);
        $("#rtenderedAmt").text(tenderedAmt);
    });

    $("#rp3").on('change', function(){
        var p1 = $("#rp1").val();
        var p2 = $("#rp2").val();
        var p3 = $(this).val();
        var p4 = $("#rp4").val();
        var p5 = $("#rp5").val();
        var p6 = $("#rp6").val();
        var p7 = $("#rp7").val();
        var totalCharge = $("#rtotalCharge").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalCharge)-Number(tenderedAmt)).toFixed(2);

        $("#rbalance").text(balance);
        $("#rtenderedAmt").text(tenderedAmt);

    });

    $("#rp4").on('change', function(){
        var p1 = $("#rp1").val();
        var p2 = $("#rp2").val();
        var p3 = $("#rp3").val();
        var p4 = $(this).val();
        var p5 = $("#rp5").val();
        var p6 = $("#rp6").val();
        var p7 = $("#rp7").val();
        var totalCharge = $("#rtotalCharge").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalCharge)-Number(tenderedAmt)).toFixed(2);

        $("#rbalance").text(balance);
        $("#rtenderedAmt").text(tenderedAmt);

    });

    $("#rp5").on('change', function(){
        var p1 = $("#rp1").val();
        var p2 = $("#rp2").val();
        var p3 = $("#rp3").val();
        var p4 = $("#rp4").val();
        var p5 = $(this).val();
        var p6 = $("#rp6").val();
        var p7 = $("#rp7").val();
        var totalCharge = $("#rtotalCharge").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalCharge)-Number(tenderedAmt)).toFixed(2);

        $("#rbalance").text(balance);
        $("#rtenderedAmt").text(tenderedAmt);
    });

    $("#rp6").on('change', function(){
        var p1 = $("#rp1").val();
        var p2 = $("#rp2").val();
        var p3 = $("#rp3").val();
        var p4 = $("#rp4").val();
        var p5 = $("#rp5").val();
        var p6 = $(this).val();
        var p7 = $("#rp7").val();
        var totalCharge = $("#rtotalCharge").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalCharge)-Number(tenderedAmt)).toFixed(2);

        $("#rbalance").text(balance);
        $("#rtenderedAmt").text(tenderedAmt);

    });

    $("#rp7").on('change', function(){
        var p1 = $("#rp1").val();
        var p2 = $("#rp2").val();
        var p3 = $("#rp3").val();
        var p4 = $("#rp4").val();
        var p5 = $("#rp5").val();
        var p6 = $("#rp6").val();
        var p7 = $(this).val();
        var totalCharge = $("#rtotalCharge").text();
        var balance ;
        var tenderedAmt;

        tenderedAmt = (Number(p1) + Number(p2) + Number(p3) + Number(p4) + Number(p5) + Number(p6) + Number(p7)).toFixed(2);
        balance = (Number(totalCharge)-Number(tenderedAmt)).toFixed(2);

        $("#rbalance").text(balance);
        $("#rtenderedAmt").text(tenderedAmt);

    });

    $(".rescheduleBooking").on('hidden.bs.modal', function(){

        $('#resetBtn').click();
    });

    $('#resetBtn').on('click', function(){
        $('#checkAvailabilityBtn').show();
        $('#showAmendment').hide();
        $('#showRPayment').hide();
        $('#showPaymentBtn').hide();

        $('#rtotalPayable').text("0.00");

        document.getElementById("rChaletTypeId").disabled=false;
        document.getElementById("rChaletId").disabled=false;
        document.getElementById("rCheckInDate").disabled=false;
        document.getElementById("rCheckOutDate").disabled=false;

        $("#summaryRBody").remove();
        //default table data (No records found)
        var tbody = document.createElement('tbody'), tr, td, row, cell;
        tbody.id="summaryRBody";
        tr = document.createElement('tr');
        td1 = document.createElement('td');
        td1.setAttribute('colSpan','5');
        tr.appendChild(td1);
        td1.innerHTML = "No record(s) found!";
        tbody.appendChild(tr);
        document.getElementById('summaryRTable').appendChild(tbody);

    });

    $("#paymentBtn").click(function(e){
        e.preventDefault();

        var getConfirm =  $('#paymentTxt').text();

        var totalPayable = $("#rtotalPayable").text();
        var tenderedAmt = $("#rtenderedAmt").text();
        var balance = $("#rbalance").text();
        var charge = $("#rtotalCharge").text();

        $("#rPayable").val(totalPayable);
        $("#rTenderAmt").val(tenderedAmt);
        $("#rCharge").val(charge);

        if(getConfirm=="Confirm"){

            var action = "refund";
            var charge = $("#rtotalCharge").text();

            $("#rAction").val(action);
            $("#rCharge").val(charge);

            document.getElementById("rChaletTypeId").disabled=false;
            document.getElementById("rChaletId").disabled=false;
            //document.getElementById("rbook").disabled=false;
            document.getElementById("rCheckInDate").disabled=false;
            document.getElementById("rCheckOutDate").disabled=false;

            this.form.submit();

        }else{

            var action = "charge";

            var amountBalance = $("#rbalance").text();

            var d1 = $("#rd1").val();
            var d2 = $("#rd2").val();
            var d3 = $("#rd3").val();
            var d4 = $("#rd4").val();
            var d5 = $("#rd5").val();
            var d6 = $("#rd6").val();
            var d7 = $("#rd7").val();

            var p1 = $("#rp1").val();
            var p2 = $("#rp2").val();
            var p3 = $("#rp3").val();
            var p4 = $("#rp4").val();
            var p5 = $("#rp5").val();
            var p6 = $("#rp6").val();
            var p7 = $("#rp7").val();

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


            $("#rDesc").val(desc);
            $("#rAmount").val(amount);
            $("#rAction").val(action);

            if(amountBalance != "0.00"){
                alert(ERROR_SUBMIT);

            }else{

                document.getElementById("rChaletTypeId").disabled=false;
                document.getElementById("rChaletId").disabled=false;
               // document.getElementById("rbook").disabled=false;
                document.getElementById("rCheckInDate").disabled=false;
                document.getElementById("rCheckOutDate").disabled=false;

                this.form.submit();
            }
        }

    });
});