console.log("Hello World!");

function toggleSideBar() {
    var x = document.getElementById("sidebar");
    var y = document.getElementById("content");
    var z = document.getElementById("right");
    var w = document.getElementById("left");
    var b = document.getElementById("bar_in");
    var c = document.getElementById("leftofBar");
    if (x.style.width === "0%") {
        x.style.width = "15%";
        y.style.marginLeft = "18%";
        z.style.display = "none";
        w.style.display = "block";
        b.style.marginLeft = "15%";
        c.style.display = "block";
    } else {
        x.style.width = "0%";
        y.style.marginLeft = "3%";
        z.style.display = "block";
        w.style.display = "none";
        b.style.marginLeft = "0%";
        c.style.display = "none";
    }
  }
