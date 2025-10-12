using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Noitcua.Models;
using System.Threading.Tasks;

public class AccountController : Controller
{
    private readonly bdContext _context;

    public AccountController(bdContext context)
    {
        _context = context;
    }

    [HttpGet]
    public IActionResult Login()
    {
        return View();
    }

    [HttpGet]
    public IActionResult Register()
    {
        return View(new utilizador());
    }

    [HttpGet]
    public IActionResult ForgotPassword()
    {
        return View(new ForgotPasswordModel());
    }

    [HttpGet]
    public IActionResult ResetPassword()
    {
        return View();
    }

    [HttpGet]
    public IActionResult ResetHandle() 
    {
        return View();    
    }

    [HttpGet]
    public IActionResult Profile()
    {
        int? userId = HttpContext.Session.GetInt32("Id");
        if(userId != null)
        { 
            return View();
        }
        else
        {
            return RedirectToAction("Login");
        }
    }
    
    [HttpGet]
    public IActionResult Logout()
    {
        HttpContext.Session.Clear();
        return RedirectToAction("Login");
    }

    [HttpPost]
    public async Task<IActionResult> Login(utilizador user)
    {
        if (ModelState.IsValid)
        {
            var authenticatedUser = await _context.utilizador
                .FirstOrDefaultAsync(u => u.email == user.email && u.password == user.password);

            if (authenticatedUser != null)
            {
                HttpContext.Session.SetInt32("Id", authenticatedUser.id);
                HttpContext.Session.SetString("Handle", authenticatedUser.handle);
                HttpContext.Session.SetString("Email", authenticatedUser.email);

                var admin = await _context.admin.FirstOrDefaultAsync(a => a.id_user == authenticatedUser.id);
                if (admin != null)
                {
                    HttpContext.Session.SetInt32("Admin", 1);
                }
                else
                {
                    HttpContext.Session.SetInt32("Admin", 0);
                }
                return RedirectToAction("Index", "Home");
            }

            ModelState.AddModelError(string.Empty, "Dados de login inválidos. Tente Novamente.");
        }

        return View(user);
    }

    [HttpPost]
    public async Task<IActionResult> Register(utilizador newUser)
    {
        if (ModelState.IsValid)
        {
            var existingUser = await _context.utilizador
                .FirstOrDefaultAsync(u => u.email == newUser.email || u.handle == newUser.handle);

            if (existingUser == null)
            {
                _context.Add(newUser);
                await _context.SaveChangesAsync();
                return RedirectToAction("Login");
            }
            else
            {
                ModelState.AddModelError(string.Empty, "Já existe um utilizador com o mesmo email ou handle.");
            }
        }
        return View(newUser);
    }

    [HttpPost]
    public async Task<IActionResult> ForgotPassword(ForgotPasswordModel model)
    {
        if (ModelState.IsValid)
        {
            var user = await _context.utilizador
                .FirstOrDefaultAsync(u => u.email == model.Email && u.handle == model.Handle);

            if (user != null)
            {
                if (model.Password == model.ConfirmPassword)
                {
                    user.password = model.Password;
                    await _context.SaveChangesAsync();
                    return RedirectToAction("Login");
                }
                else
                {
                    ModelState.AddModelError(string.Empty, "Palavras passe não coincidem.");
                }
            }
            else
            {
                ModelState.AddModelError(string.Empty, "A conta não existe.");
            }
        }
        return View(model);
    }

    [HttpPost]
    public async Task<IActionResult> ResetPassword(ResetPasswordModel model)
    {
        if (ModelState.IsValid)
        {
            var user = await _context.utilizador.FindAsync(HttpContext.Session.GetInt32("Id"));
            if (user != null)
            {
                user.password = model.Password;
                await _context.SaveChangesAsync();
                return RedirectToAction("Profile");
            }
            ModelState.AddModelError(string.Empty, "User not found.");
        }
        return View(model);
    }

    [HttpPost]
    public async Task<IActionResult> ResetHandle(string Handle)
    {
            var user = await _context.utilizador.FindAsync(HttpContext.Session.GetInt32("Id"));
            if (user != null && !user.handle.Equals(Handle) && Handle.Length>3 && Handle.Length<=20)
            {
                var handleExists = await _context.utilizador.AnyAsync(u => u.handle == Handle);
                if (!handleExists)
                {
                    user.handle = Handle;
                    HttpContext.Session.SetString("Handle", Handle);
                    await _context.SaveChangesAsync();

                    return RedirectToAction("Profile");
                }
                else
                {
                    ModelState.AddModelError("", "Handle já existe. Tente novamente.");
                }
            }
            else
            {
                ModelState.AddModelError("", "Handle inválido. Tente novamente.");
            }
        return View();
        }

}
