import jwtDecode from 'jwt-decode';

interface JwtPayload {
  sub: string;  // usuário ID
  exp: number;  // expiração
  // outros campos que possam existir no token
}

export const getLoggedUserId = (): number | null => {
  const token = localStorage.getItem('token');
  if (!token) return null;
  
  try {
    const decoded = jwtDecode<JwtPayload>(token);
    return parseInt(decoded.sub);
  } catch (error) {
    console.error('Erro ao decodificar token:', error);
    return null;
  }
};
