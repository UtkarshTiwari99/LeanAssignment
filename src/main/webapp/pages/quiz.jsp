<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Quiz Page</title>

    <!-- style.css -->

    <style>
      * {
        padding: 0;
        margin: 0;
        box-sizing: border-box;
      }
      body {
        font-family: "Lato";
      }
      .wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100vw;
        height: 100vh;
        background-color: #341f97;
      }

      .welcome_text {
        width: 400px;
      }

      .welcome_form {
      }

      .welcome_form input,
      .welcome_form button {
        display: block;
        width: 100%;
      }

      .welcome_form input {
        background-color: transparent;
        color: #fff;
        font-size: 30px;
        text-align: center;
        border: none;
        border-bottom: 1px solid #fff;
        margin-bottom: 15px;
      }

      .welcome_form input:focus {
        outline: none;
      }

      .welcome_form button {
        padding: 20px 0;
        border: none;
        border-radius: 100px;
        background-color: #fff;
        color: #341f97;
        font-size: 22px;
        transition: 0.4s all;
      }

      .welcome_form button:hover {
        box-shadow: 0px 0px 10px 1px rgba(0, 0, 0, 0.2);
        transform: translateY(-5px);
        cursor: pointer;
      }

      .quiz {
        display: grid;
        grid-template-rows: 60px auto;
        width: 800px;
        height: 500px;
        background-color: #fff;
        box-shadow: 0px 0px 10px 3px rgba(0, 0, 0, 0.3);
        border-radius: 2px;
      }

      .quiz_header {
        display: flex;
        justify-content: space-between;
        border-top-left-radius: 3px;
        border-top-right-radius: 3px;
        background-color: #fff;
        box-shadow: 0px 2px 5px 1px rgba(0, 0, 0, 0.1);
        z-index: 1;
      }
      .quiz_body {
        padding: 30px 30px;
      }
      .quiz_user {
        display: flex;
        align-items: center;
        height: 100%;
        padding-left: 30px;
      }

      .quiz_timer {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        width: 100px;
        background-color: #341f97;
        color: #fff;
      }
      .quiz_body {
        background-color: #ecf0f1;
      }

      .option_group {
        list-style-type: none;
        margin: 30px 0;
      }

      .option {
        display: block;
        width: 300px;
        background-color: #fff;
        margin-bottom: 20px;
        padding: 15px 20px;
        border-radius: 100px;
        border: 2px solid transparent;
        transition: 0.4s all;
      }

      .option:hover {
        cursor: pointer;
        /* background-color: rgba(255, 255, 255, 0.1); */
        border: 2px solid #341f97;
        color: #341f97;
      }
      .option.active {
        background-color: #341f97;
        color: #fff;
      }
      .btn-next {
        border: none;
        padding: 15px 35px;
        background-color: #341f97;
        color: #fff;
        border-radius: 27px;
        transition: 0.4s all;
      }

      .btn-next:hover {
        cursor: pointer;
        background-color: #fff;
        color: #341f97;
        box-shadow: 0px 0px 10px 1px rgba(0, 0, 0, 0.1);
      }

      .award_icon {
        display: block;
        font-size: 300px;
        color: #fff;
      }

      .username,
      .userpoints,
      .usertime {
        color: #fff;
        text-align: center;
        margin-top: 15px;
      }
    </style>

  </head>
  <body>
    <div class="wrapper">
      <div class="quiz">
        <div class="quiz_header">
          <div class="quiz_user">
            <h4>Welcome! <span class="name"></span></h4>
          </div>
          <div class="quiz_timer">
            <span class="time">00:00</span>
          </div>
        </div>
        <div class="quiz_body">
          <div id="questions">
            <h2>Q${testData.questions.get(0).questionNumber}. ${testData.questions.get(0).statement}</h2>
            <c:forEach var="item" items="${testData.questions.get(0).options}">
              <li class="option" >${item.op_number} - ${item.content}</li>
            </c:forEach>
          </div>
          <button class="btn-next" onclick="next()">Next Question</button>
        </div>
      </div>
    </div>

    <!-- site.js -->
    <script>
      let questions = [
        {
          id: 1,
          question: "What is the full form of RAM ?",
          answer: "Random Access Memory",
          options: [
            "Random Access Memory",
            "Randomely Access Memory",
            "Run Aceapt Memory",
            "None of these"
          ]
        },
        {
          id: 2,
          question: "What is the full form of CPU?",
          answer: "Central Processing Unit",
          options: [
            "Central Program Unit",
            "Central Processing Unit",
            "Central Preload Unit",
            "None of these"
          ]
        },
        {
          id: 3,
          question: "What is the full form of E-mail",
          answer: "Electronic Mail",
          options: [
            "Electronic Mail",
            "Electric Mail",
            "Engine Mail",
            "None of these"
          ]
        }
      ];

      let question_count = 0;
      let points = 0;

      window.onload = function() {
        show(question_count);
      };

      function next() {
        // if the question is last then redirect to final page
        if (question_count == questions.length - 1) {
          sessionStorage.setItem("time", time);
          clearInterval(mytime);
          location.href = "${pageContext.request.contextPath}/end";
        }
        console.log(question_count);

        let user_answer = document.querySelector("li.option.active").innerHTML;
        // check if the answer is right or wrong
        if (user_answer == questions[question_count].answer) {
          points += 10;
          sessionStorage.setItem("points", points);
        }
        console.log(points);
        question_count++;
        show(question_count);
      }

      function show(count) {
        let question = document.getElementById("questions");
        let [first, second, third, fourth] = questions[count].options;

        question.innerHTML = `
          <h2>Q${count + 1}. ${questions[count].question}</h2>
          <ul class="option_group">
          <li class="option">${first}</li>
          <li class="option">${second}</li>
          <li class="option">${third}</li>
          <li class="option">${fourth}</li>
          </ul>
          `;
        toggleActive();
      }

      function toggleActive() {
        let option = document.querySelectorAll("li.option");
        for (let i = 0; i < option.length; i++) {
          option[i].onclick = function() {
            for (let i = 0; i < option.length; i++) {
              if (option[i].classList.contains("active")) {
                option[i].classList.remove("active");
              }
            }
            option[i].classList.add("active");
          };
        }
      }

      let dt = new Date(new Date().setTime(0));
      let ctime = dt.getTime();
      let seconds = Math.floor((ctime % (1000 * 60))/ 1000);
      let minutes = Math.floor((ctime % (1000 * 60 * 60))/( 1000 * 60));
      console.log(seconds, minutes);
      let time = 0;
      let mytime = setInterval(function(){
        time++;

        if(seconds < 59) {
          seconds++;
        } else {
          seconds = 0;
          minutes++;
        }
        let formatted_sec = seconds < 10 ? "0"+seconds: seconds;
        let formatted_min = minutes < 10 ? "0"+minutes: minutes;
        document.querySelector("span.time").innerHTML = formatted_min+" : "+formatted_sec;
      }, 1000);


      // let user_name = sessionStorage.getItem("name");
      // let user_points = sessionStorage.getItem("points");
      // let user_time = sessionStorage.getItem("time");
      // document.querySelector("span.name").innerHTML = user_name;
      // document.querySelector("span.points").innerHTML = user_points;
      // document.querySelector("span.time_taken").innerHTML = user_time;
    </script>
  </body>
</html>