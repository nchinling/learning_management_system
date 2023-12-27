import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { QuizService } from '../services/quiz.service';
import { AccountService } from '../services/account.service';
import { CreateContentResponse, CreateQuizResponse } from '../models';
import { ContentService } from '../services/content.service';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  router = inject(Router)
  contentSvc = inject(ContentService)
  quizSvc = inject(QuizService)
  accountSvc = inject(AccountService)
  accountId!: string
  allQuizCreated$!:Promise<CreateQuizResponse[]>
  allContentCreated$!: Promise<CreateContentResponse[]>

  ctx!:any
  ctx2!:any
  ctx3!:any


  ngOnInit(): void {
    // this.createChart();
    this.accountId = this.accountSvc.account_id
    console.log('Account id at dashboard is ', this.accountId)
    this.allQuizCreated$ = this.quizSvc.getAllQuizCreated(this.accountId)
    this.allContentCreated$ = this.contentSvc.getAllContentCreated(this.accountId)
    // this.createChart();
    this.createOrUpdateCharts();
  }

  // ngAfterViewInit(): void {
  //   // this.createChart();
  // }


  viewQuiz(quiz_id:string){
    console.info('Printed the quiz_id:'+ quiz_id)
    this.quizSvc.quiz_id = quiz_id 
    this.router.navigate(['viewquiz']);
  
  }

  viewContent(content_id:string){
    console.info('Printed the content_id:'+ content_id)
    this.contentSvc.content_id = content_id 
    this.router.navigate(['editnotes']);
  
  }

  private createOrUpdateCharts(): void {


    const canvasContexts = [this.ctx, this.ctx2, this.ctx3];

    for (const ctx of canvasContexts) {
      if (ctx) {
        const chartInstance = Chart.getChart(ctx);
        if (chartInstance) {
          chartInstance.destroy();
        }
      }
    }

    
    // Get the canvas elements after destroying the previous charts.
    this.ctx = document.getElementById('allQuizAttempts');
    this.ctx2 = document.getElementById('allContentCreated');
    this.ctx3 = document.getElementById('eachQuizAverage');
    

    if (this.ctx) {
      this.updateChart();
    }

  }


  
  private updateChart(): void {

      this.allQuizCreated$.then(data => {

        const filteredData = data.filter(quiz => quiz.quiz_id != null);

        if (filteredData.length > 0) {
          const labels = filteredData.map(quiz => quiz.title);
          const quizAttemptsData = filteredData.map(quiz => quiz.attempts);
          const quizAverageData = filteredData.map(quiz => {
            const studentTotalMarks = parseInt(quiz.student_total_marks, 10); 
            const quizTotalMarks = parseInt(quiz.quiz_total_marks, 10); 
          
          //Perform calculation
            if (!isNaN(studentTotalMarks) && !isNaN(quizTotalMarks) && quiz.attempts !== 0) {
              return (studentTotalMarks / (quizTotalMarks * quiz.attempts))*100;
            } else {
           
              return 0;
            }
          });
          
          
   
          // const backgroundColors = labels.map(() => this.generateRandomColor());
          const backgroundColors = labels.map(() => this.generateRandomColor());
          const borderColors = labels.map(() => this.generateRandomColor());
          const backgroundColors2 = labels.map(() => this.generateRandomColor());
          const borderColors2 = labels.map(() => this.generateRandomColor());

          if (this.ctx) {

          const maxDataValue = Math.max(...quizAttemptsData);
          const suggestedMaxValue = maxDataValue +1;

            const chart = new Chart(this.ctx, {
              // type: 'pie',
              type: 'bar',
              data: {
                labels: labels,
                datasets: [{
                  data: quizAttemptsData,
                  label: '',
                  backgroundColor: backgroundColors,
                  borderColor: borderColors,
                  borderWidth: 1
                }]
              },
              options: {
                plugins: {
                  title: {
                      display: true,
                      text: 'Quiz Attempts'
                  }
                },
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  y: {
                    beginAtZero: true,
                    suggestedMax: suggestedMaxValue,
                  }
                }
              }
            });
          }



          if (this.ctx3) {

            // const maxDataValue = Math.max(...quizAverageData);
            const maxDataValue = 100;
            // const suggestedMaxValue = maxDataValue +1;
  
              const chart = new Chart(this.ctx3, {
                // type: 'pie',
                type: 'bar',
                data: {
                  labels: labels,
                  datasets: [{
                    data: quizAverageData,
                    label: '',
                    backgroundColor: backgroundColors2,
                    borderColor: borderColors2,
                    borderWidth: 1
                  }]
                },
                options: {
                  plugins: {
                    tooltip: {
                      callbacks: {
                          label: function(tooltipItem) {
                            // return tooltipItem.raw + '%';
                            const value = (tooltipItem.raw as number).toFixed(1); 
                            return value + '%';

                          }
                      }
                  },
                    title: {
                        display: true,
                        text: 'Quiz Average Score (%)'
                    }

                  },
                  responsive: true,
                  maintainAspectRatio: false,
                  scales: {
                    y: {
                      beginAtZero: true,
                      suggestedMax: maxDataValue,
                      ticks: {
                        callback: function (value) {
                          return value + '%'; 
                        }
                      }

                    }
                  }
      
                }
              });
            }






        }
      });

      


      this.allContentCreated$.then(data => {

        const filteredData = data.filter(content => content.content_id != null);

        if (filteredData.length > 0) {
          const labels = filteredData.map(content => content.title);
          const datasetData = filteredData.map(content => content.number_of_times_accessed);
   
          // const backgroundColors = labels.map(() => this.generateRandomColor());
          const backgroundColors = labels.map(() => this.generateRandomColor());
          const borderColors = labels.map(() => this.generateRandomColor());

          if (this.ctx) {

          const maxDataValue = Math.max(...datasetData);
          const suggestedMaxValue = maxDataValue +1;

            const chart = new Chart(this.ctx2, {
              // type: 'pie',
              type: 'bar',
              data: {
                labels: labels,
                datasets: [{
                  data: datasetData,
                  label: '',
                  backgroundColor: backgroundColors,
                  borderColor: borderColors,
                  borderWidth: 1
                }]
              },
              options: {
                plugins: {
                  title: {
                      display: true,
                      text: 'Notes accessed'
                  }
                },
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  y: {
                    beginAtZero: true,
                    suggestedMax: suggestedMaxValue,
                  }
                }
              }
            });
          }
        }
      });
    
  }


  private generateRandomColor(): string {
    // Function to generate a random hex color
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }


 

}
