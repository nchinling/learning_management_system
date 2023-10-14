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
    timestamp: string
    key: string
}

export interface RegisterResponse {
    account_id: string
    username: string
    timestamp: string
}

export interface Quiz {
    account_id: string
    title: string
    questions: QuizQuestion[]
}

export interface QuizQuestion {
    quiz_question_id: string
    question: string
    questionType: string
    options: (string | null)[];
    answer: string
}


export interface CreateQuizResponse {
    account_id: string
    quiz_id: string
    title: string
    status: string
}











