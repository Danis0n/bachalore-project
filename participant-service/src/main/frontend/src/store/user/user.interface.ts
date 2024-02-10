export interface UsernamePassword {
    login: string;
    password: string
}

export interface AuthResponse {
    participant: Participant
    tokens: JWTTokenPair
}

export interface Participant {
    name: string
    bic: string
    typeName: string
    login: string
}

export interface JWTTokenPair {
    accessToken: string
    refreshToken: string
}