export interface UserData {
    account_id: string
    name: string
    username: string
    email: string
    password: string
}

export interface LoginResponse {
    account_id: string
    username: string
    name: string
    timestamp: string
    key: string
    studentClass: string
}

export interface RegisterResponse {
    account_id: string
    username: string
    timestamp: string
}

export interface Quiz {
    account_id: string
    quiz_id: string
    title: string
    questions: QuizQuestion[]
    classes: string[]
    date_created: string
    date_edited: string
}

export interface QuizQuestion {
    // quiz_question_id: string
    question: string
    questionType: string
    option1: (string | null);
    option2: (string | null);
    option3: (string | null);
    option4: (string | null);
    answer: string
    marks: number
}


export interface CreateQuizResponse {
    account_id: string
    quiz_id: string
    title: string
    status: string
    date_created: string
    date_edited: string
    quiz_total_marks: string
    student_total_marks: string
    percent: string
    date_attempted: string
    // quizBuilder.add("quiz_total_marks", result.getQuizTotalMarks());
    // quizBuilder.add("student_total_marks", result.getStudentTotalMarks());
    // quizBuilder.add("percent", result.getPercent());
}

export interface MarkedQuizResponse {
    account_id: string
    quiz_id: string
    title: string
    questions: QuizQuestion[]
    marks: string
    total_marks: string
}











