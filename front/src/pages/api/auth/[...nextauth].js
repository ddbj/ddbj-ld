import NextAuth from 'next-auth';

import { nextAuthOptions } from '../../../services/nextauth/options';

export default NextAuth(nextAuthOptions);
