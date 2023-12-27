import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CreateContentResponse, CreateQuizResponse } from '../models';
import { AccountService } from '../services/account.service';
import { QuizService } from '../services/quiz.service';
import { ContentService } from '../services/content.service';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-studentdashboard',
  templateUrl: './studentdashboard.component.html',
  styleUrls: ['./studentdashboard.component.css']
})
export class StudentdashboardComponent implements OnInit {

  router = inject(Router)
  quizSvc = inject(QuizService)
  contentSvc = inject(ContentService)
  accountSvc = inject(AccountService)
  studentClass!: string
  name!: string
  allStudentQuizCreated$!:Promise<CreateQuizResponse[]>
  allStudentContentCreated$!: Promise<CreateContentResponse[]>
  ctx!:any

  ngOnInit(): void {

    this.name = this.accountSvc.name
    this.studentClass = this.accountSvc.studentClass
    console.log('Student class at student dashboard is ', this.studentClass)
    this.allStudentQuizCreated$ = this.quizSvc.getAllStudentQuiz(this.studentClass)
    this.allStudentContentCreated$ = this.contentSvc.getAllStudentContent(this.studentClass)
    this.createOrUpdateCharts()
  }


  viewQuiz(quiz_id:string){
    console.info('Printed the quiz_id:'+ quiz_id)
    this.quizSvc.quiz_id = quiz_id 
    this.router.navigate(['studentquiz']);
  
  }

  viewContent(content_id:string){
    console.info('Printed the content_id:'+ content_id)
    this.contentSvc.content_id = content_id 
    this.router.navigate(['studentcontent']);
  
  }


  
  private createOrUpdateCharts(): void {

    const canvasContexts = [this.ctx];

    for (const ctx of canvasContexts) {
      if (ctx) {
        const chartInstance = Chart.getChart(ctx);
        if (chartInstance) {
          chartInstance.destroy();
        }
      }
    }

    // Get the canvas elements after destroying the previous charts.
    this.ctx = document.getElementById('allQuizAverageResults');

  
    if (this.ctx) {
      this.updateChart();
    }

  }


  
  private updateChart(): void {

      this.allStudentQuizCreated$.then(data => {

        const filteredData = data.filter(quiz => quiz.quiz_id != null);

        if (filteredData.length > 0) {
          const labels = filteredData.map(quiz => quiz.title);
          const quizAverageData = filteredData.map(quiz => parseFloat(quiz.percent));

            
          const backgroundColors = labels.map(() => this.generateRandomColor());
          const borderColors = labels.map(() => this.generateRandomColor());


          if (this.ctx) {

          const maxDataValue = Math.max(...quizAverageData);
          const suggestedMaxValue = maxDataValue +1;

            const chart = new Chart(this.ctx, {
              // type: 'pie',
              type: 'bar',
              data: {
                labels: labels,
                datasets: [{
                  data: quizAverageData,
                  label: '',
                  backgroundColor: backgroundColors,
                  borderColor: borderColors,
                  borderWidth: 1
                }]
              },
              options: {
                indexAxis: 'y',
                plugins: {
                  title: {
                      display: true,
                      text: 'Average score (%)'
                  }
                },
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  // x: {
                  //   beginAtZero: true,
                  //   suggestedMax: suggestedMaxValue,
                  // }
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
