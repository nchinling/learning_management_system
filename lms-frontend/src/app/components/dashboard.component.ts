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


  ngOnInit(): void {
    // this.createChart();
    this.accountId = this.accountSvc.account_id
    console.log('Account id at dashboard is ', this.accountId)
    this.allQuizCreated$ = this.quizSvc.getAllQuizCreated(this.accountId)
    this.allContentCreated$ = this.contentSvc.getAllContentCreated(this.accountId)
    this.createChart();
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


  createChart() {
    // const ctx = document.getElementById('acquisitions') as HTMLCanvasElement;
    this.ctx = document.getElementById('acquisitions')
    const myChart = new Chart(this.ctx, {
      type: 'bar',
      data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
          label: '# of Votes',
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }


  private createOrUpdateCharts(): void {
    if (this.ctx2) {
      const chartInstance = Chart.getChart(this.ctx); 
      if (chartInstance) {
        chartInstance.destroy(); 
      }
    }
    
    // Get the canvas elements after destroying the previous charts.
    this.ctx2 = document.getElementById('myChart');

    if (this.ctx2) {
      this.updateChart();
    }

  }


  
  private updateChart(): void {

      this.allQuizCreated$.then(data => {

        const filteredData = data.filter(quiz => quiz.quiz_id != null);

        if (filteredData.length > 0) {
          const labels = filteredData.map(quiz => quiz.title);
          const datasetData = filteredData.map(quiz => quiz.attempts);
   
          // const backgroundColors = labels.map(() => this.generateRandomColor());

          if (this.ctx2) {

          const maxDataValue = Math.max(...datasetData);
          const suggestedMaxValue = maxDataValue +1;

            const chart = new Chart(this.ctx2, {
              // type: 'pie',
              type: 'bar',
              data: {
                labels: labels,
                datasets: [{
                  label: 'Number of attempts',
                  data: datasetData,
                  backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                  ],
                  borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                  ],
                  borderWidth: 1
                }]
              },
              options: {
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
